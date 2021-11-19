package com.example.logistics.service;

import com.example.logistics.model.dto.OrderDTO;
import com.example.logistics.model.entity.Order;
import com.example.logistics.model.query.OrderQuery;
import com.example.logistics.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService extends CrudService<Order, Long> {

    Page<Order> queryBy(OrderQuery query, Pageable pageable);

    boolean cancelOrder(Long id);

    OrderDTO toDto(Order order);

}
