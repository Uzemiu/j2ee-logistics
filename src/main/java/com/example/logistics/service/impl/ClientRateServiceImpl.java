package com.example.logistics.service.impl;

import com.example.logistics.model.dto.ClientRateDTO;
import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.ClientRate;
import com.example.logistics.model.entity.Order;
import com.example.logistics.model.enums.OrderStatus;
import com.example.logistics.model.param.ClientRateParam;
import com.example.logistics.repository.ClientRateRepository;
import com.example.logistics.service.ClientRateService;
import com.example.logistics.service.OrderService;
import com.example.logistics.service.base.AbstractCrudService;
import com.example.logistics.utils.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service("clientRateService")
public class ClientRateServiceImpl extends AbstractCrudService<ClientRate, Long> implements ClientRateService {

    private final ClientRateRepository clientRateRepository;

    private final OrderService orderService;

    protected ClientRateServiceImpl(ClientRateRepository repository,
                                    OrderService orderService) {
        super(repository);
        this.clientRateRepository = repository;
        this.orderService = orderService;
    }

    @Override
    public ClientRate rateOrder(ClientRateParam rate) {
        Assert.isTrue(SecurityUtil.isClient(), "只有客户才能评价订单");
        Client client = (Client) SecurityUtil.getCurrentUser();
        Order order = orderService.getNotNullById(rate.getOrderId());
        Assert.isTrue(order.getSender().equals(client)
                || order.getReceiverPhone().equals(client.getPhoneNumber()), "你不能评价他人的订单");
        Assert.isTrue(clientRateRepository.countByOrderAndClient(order, client) == 0, "您已评价过当前订单");
        Assert.isTrue(order.getStatus().equals(OrderStatus.RECEIPT_CONFIRMED), "只能评价已收货的订单");

        ClientRate clientRate = new ClientRate();
        BeanUtils.copyProperties(rate, clientRate);
        clientRate.setClient(client);
        clientRate.setOrder(order);

        return clientRateRepository.save(clientRate);
    }

    @Override
    public Optional<ClientRate> getByOrder(Order order) {
        Assert.notNull(order, "Order must not be null");
        return clientRateRepository.findByOrder(order);
    }

    @Override
    public Optional<ClientRate> getByOrderAndClient(Order order, Client client) {
        return clientRateRepository.findByOrderAndClient(order, client);
    }

    @Override
    public ClientRateDTO toDto(ClientRate clientRate) {
        if(clientRate == null){
            return null;
        }
        ClientRateDTO dto = new ClientRateDTO();
        BeanUtils.copyProperties(clientRate, dto);
        return dto;
    }
}
