package com.example.logistics.service.impl;

import com.example.logistics.model.entity.Order;
import com.example.logistics.model.query.OrderQuery;
import com.example.logistics.repository.BaseRepository;
import com.example.logistics.repository.OrderRepository;
import com.example.logistics.service.OrderService;
import com.example.logistics.service.base.AbstractCrudService;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("orderService")
public class OrderServiceImpl extends AbstractCrudService<Order, Long> implements OrderService {

    private final OrderRepository orderRepository;

    protected OrderServiceImpl(OrderRepository repository) {
        super(repository);
        this.orderRepository = repository;
    }

    @Override
    public Page<Order> queryBy(OrderQuery query, Pageable pageable) {
        return orderRepository.findAll(query.toSpecification(), pageable);
    }
}
