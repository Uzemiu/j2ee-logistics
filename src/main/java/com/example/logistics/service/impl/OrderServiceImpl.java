package com.example.logistics.service.impl;

import com.example.logistics.model.dto.OrderDTO;
import com.example.logistics.model.entity.*;
import com.example.logistics.model.enums.OrderStatus;
import com.example.logistics.model.enums.VehicleStatus;
import com.example.logistics.model.query.OrderQuery;
import com.example.logistics.repository.OrderRepository;
import com.example.logistics.repository.TransportTraceRepository;
import com.example.logistics.service.ClientService;
import com.example.logistics.service.OrderService;
import com.example.logistics.service.VehicleService;
import com.example.logistics.service.base.AbstractCrudService;
import com.example.logistics.utils.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

@Service("orderService")
public class OrderServiceImpl extends AbstractCrudService<Order, Long> implements OrderService {

    private final OrderRepository orderRepository;

    private final ClientService clientService;

    private final VehicleService vehicleService;

    private final TransportTraceRepository transportTraceRepository;

    protected OrderServiceImpl(OrderRepository repository,
                               ClientService clientService,
                               VehicleService vehicleService,
                               TransportTraceRepository transportTraceRepository) {
        super(repository);
        this.orderRepository = repository;
        this.clientService = clientService;
        this.vehicleService = vehicleService;
        this.transportTraceRepository = transportTraceRepository;
    }

    @Override
    public Page<Order> queryBy(OrderQuery query, Pageable pageable) {
        return orderRepository.findAll(query.toSpecification(), pageable);
    }

    @Override
    public boolean cancelOrder(Long id) {
        Order order = getNotNullById(id);
        if(order.getStatus().lessThan(OrderStatus.ALREADY_ARRIVED)){
            // 订单已到达或已结束（确认收货，已取消，丢失）则不能取消订单
            setOrderStatus(order, OrderStatus.CANCELLED);
            // ...省略无数操作
            orderRepository.save(order);
            TransportTrace trace = new TransportTrace();
            trace.setOrder(order);
            trace.setOrderStatus(OrderStatus.CANCELLED);
            trace.setInformation("订单已被取消");
            transportTraceRepository.save(trace);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Order update(Order order) {
        Order old = getNotNullById(order.getId());
        User current = SecurityUtil.getCurrentUser();
        if(current instanceof Client){
            Assert.isTrue(old.getSender().equals(current), "你没有权限这么做");
        }
        // TODO: 2021/11/17 管理员是否能更改订单状态 以及价格
        if(old.getStatus().lessThanOrEqual(OrderStatus.NOT_SENT)){
            // 未发货的情况下才能修改收寄件信息
            old.setSendAddress(order.getSendAddress());
            old.setSenderName(order.getSenderName());
            old.setReceiveAddress(order.getReceiveAddress());
            old.setReceiverName(order.getReceiverName());
            old.setReceiverPhone(order.getReceiverPhone());
            old.setItemName(order.getItemName());
            old.setItemVolume(order.getItemVolume());
            old.setItemWeight(order.getItemWeight());
            old.setItemCount(order.getItemCount());
            if(current instanceof Employee){
                assignVehicle(old, order.getTransportVehicle());
            }
        }
        old.setComment(order.getComment());
        orderRepository.save(old);
        return old;
    }

    @Transactional
    @Override
    public Order create(Order order) {
        Order o = orderRepository.save(order);
        TransportTrace trace = new TransportTrace();
        trace.setOrder(o);
        trace.setOrderStatus(o.getStatus());
        trace.setInformation("创建订单");
        transportTraceRepository.save(trace);
        return o;
    }

    @Override
    public OrderDTO toDto(Order order) {
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(order, dto);
        dto.setSender(clientService.toDto(order.getSender()));
        return dto;
    }

    @Transactional
    @Override
    public void confirmOrder(Long id) {
        Order order = getNotNullById(id);
        Client client = (Client) SecurityUtil.getCurrentUser();
        Assert.isTrue(order.getSender().equals(client), "你没有权限这么做");
        Assert.isTrue(order.getStatus().between(OrderStatus.NOT_SENT, OrderStatus.ALREADY_ARRIVED), "只有未收货的订单才能确认收货");
        order.setStatus(OrderStatus.RECEIPT_CONFIRMED);
        orderRepository.save(order);

        TransportTrace trace = new TransportTrace();
        trace.setOrder(order);
        trace.setOrderStatus(OrderStatus.RECEIPT_CONFIRMED);
        trace.setInformation("确认收货");
        transportTraceRepository.save(trace);
    }
    
    private void assignVehicle(Order order, Long vehicleId){
        if(vehicleId != null){
            assignVehicle(order, vehicleService.getNotNullById(vehicleId));
        }
    }
    
    private void assignVehicle(Order order, Vehicle newVehicle){
        // 实际应该考虑同步问题，可以使用悲观锁
        Vehicle oldVehicle = order.getTransportVehicle();
        if(!Objects.equals(oldVehicle, newVehicle)){
            if(newVehicle != null){
                newVehicle = vehicleService.getNotNullById(newVehicle.getId());
                Assert.isTrue(VehicleStatus.IDLE.equals(newVehicle.getStatus()), "分配的车辆必须是空闲状态");
                newVehicle.setStatus(VehicleStatus.TASK_ASSIGNED);
                vehicleService.update(newVehicle);
            }
            if(oldVehicle != null){
                oldVehicle.setStatus(VehicleStatus.IDLE);
                vehicleService.update(oldVehicle);
            }
            order.setTransportVehicle(newVehicle);
        }
    }

    /**
     * 设置订单状态
     * 如果订单状态已送达，已完成，则另外更新运输车辆状态
     * @param order /
     */
    private void setOrderStatus(Order order, OrderStatus status){
        order.setStatus(status);
        if(status.greaterThanOrEqual(OrderStatus.ALREADY_ARRIVED)){
            Vehicle vehicle = order.getTransportVehicle();
            if(vehicle != null){
                vehicle.setStatus(VehicleStatus.IDLE);
            }
        }
    }

    @Override
    public Order deleteById(Long aLong) {
        // TODO: 11/20/2021 管理员是否可以删除订单 
        Order order = super.deleteById(aLong);
        
        return order;
    }
}
