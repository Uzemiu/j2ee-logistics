package com.example.logistics.controller;

import com.example.logistics.annotation.AnonymousAccess;
import com.example.logistics.annotation.Permission;
import com.example.logistics.model.dto.OrderDTO;
import com.example.logistics.model.dto.PageDTO;
import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.entity.Order;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.enums.OrderStatus;
import com.example.logistics.model.enums.Role;
import com.example.logistics.model.query.OrderQuery;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.service.OrderService;
import com.example.logistics.utils.BeanUtil;
import com.example.logistics.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Permission(allowClient = true)
    @GetMapping("/mine")
    public BaseResponse<?> listOrderByCurrentUser(OrderQuery query,
                                                  @PageableDefault(sort = {"createTime"},
                                                          direction = Sort.Direction.DESC) Pageable pageable){
        query.setSender((Client) SecurityUtil.getCurrentUser());
        Page<Order> orders = orderService.queryBy(query, pageable);
        return BaseResponse.ok("ok", new PageDTO<>(orders));
    }

    @Permission(allowRoles = Role.ADMIN)
    @GetMapping("/list")
    public BaseResponse<?> listOrder(OrderQuery query,
                                     @PageableDefault(sort = {"createTime"},
                                             direction = Sort.Direction.DESC) Pageable pageable){
        Page<Order> orders = orderService.queryBy(query, pageable);
        return BaseResponse.ok("ok", new PageDTO<>(orders));
    }

    @Permission(allowClient = true, allowRoles = Role.ADMIN)
    @GetMapping
    public BaseResponse<?> getOrderDetailById(@RequestBody Long id){

        return BaseResponse.ok("ok");
    }

    @Permission(allowClient = true)
    @PostMapping
    public BaseResponse<?> createOrder(@RequestBody Order order){
        order.setStatus(OrderStatus.NOT_PAID);
        order.setTransportVehicle(null);
        order.setPrice(BigDecimal.valueOf(Math.random() * 100));
        Order res = orderService.create(order);
        return BaseResponse.ok("ok", res.getId());
    }

    @Permission(allowClient = true, allowRoles = Role.ADMIN)
    @PutMapping
    public BaseResponse<?> updateOrder(@RequestBody Order order){
        Order old = orderService.getNotNullById(order.getId());

        User current = SecurityUtil.getCurrentUser();
        if(current instanceof Client){
            Assert.isTrue(old.getSender().equals(current), "你没有权限这么做");
        }
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
                old.setTransportVehicle(order.getTransportVehicle());
            }
        }
        old.setComment(order.getComment());
        orderService.update(old);
        return BaseResponse.ok();
    }

    @Permission(allowClient = true, allowRoles = Role.ADMIN)
    @PostMapping("cancel")
    public BaseResponse<?> cancelOrder(@RequestBody Long id){
        Order order = orderService.getNotNullById(id);
        if(order.getStatus().lessThan(OrderStatus.ALREADY_ARRIVED)){
            // 订单已到达或已结束（确认收货，已取消，丢失）则不能取消订单
            order.setStatus(OrderStatus.CANCELLED);
            // ...省略无数操作
        }
        return BaseResponse.ok();
    }

//    @Permission(allowClient = true, allowRoles = Role.ADMIN)
//    @PutMapping
//    public BaseResponse<?> updateOrder(@Validated EmployeeDTO employee){
//        orderService.update(BeanUtil.copyProperties(Order.class, employee));
//        return BaseResponse.ok();
//    }

    @Permission(allowRoles = Role.ADMIN)
    @DeleteMapping
    public BaseResponse<?> deleteOrder(@RequestBody Long id){
        orderService.deleteById(id);
        return BaseResponse.ok();
    }
}
