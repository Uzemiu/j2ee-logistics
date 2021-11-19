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
        return BaseResponse.ok("ok", new PageDTO<>(orders.map(orderService::toDto)));
    }

    @Permission(allowRoles = Role.ADMIN)
    @GetMapping("/list")
    public BaseResponse<?> listOrder(OrderQuery query,
                                     @PageableDefault(sort = {"createTime"},
                                             direction = Sort.Direction.DESC) Pageable pageable){
        Page<Order> orders = orderService.queryBy(query, pageable);
        return BaseResponse.ok("ok", new PageDTO<>(orders.map(orderService::toDto)));
    }

    @Permission(allowClient = true, allowRoles = Role.ADMIN)
    @GetMapping
    public BaseResponse<?> getOrderDetailById(@RequestBody Long id){
        Order order = orderService.getNotNullById(id);
        // TODO: 2021/11/14
        return BaseResponse.ok("ok");
    }

    @Permission(allowClient = true)
    @PostMapping
    public BaseResponse<?> createOrder(@RequestBody @Validated Order order){
        order.setSender((Client) SecurityUtil.getCurrentUser());
        order.setStatus(OrderStatus.NOT_PAID);
        order.setTransportVehicle(null);
        order.setPrice(BigDecimal.valueOf(Math.random() * 100));
        Order res = orderService.create(order);
        return BaseResponse.ok("ok", res.getId());
    }

    @Permission(allowClient = true, allowRoles = Role.ADMIN)
    @PutMapping
    public BaseResponse<?> updateOrder(@RequestBody @Validated Order order){
        orderService.update(order);
        return BaseResponse.ok();
    }

    public BaseResponse<?> assignIdleVehicle(){

        return BaseResponse.ok();
    }

    @Permission(allowClient = true, allowRoles = Role.ADMIN)
    @PostMapping("cancel")
    public BaseResponse<?> cancelOrder(@RequestBody Long id){
        orderService.cancelOrder(id);
        return BaseResponse.ok();
    }
    
    @PostMapping("confirm")
    public BaseResponse<?> confirmOrder(@RequestBody Long id){
        // TODO: 2021/11/17  
        return BaseResponse.ok();
    }

    @Permission(allowRoles = Role.ADMIN)
    @DeleteMapping
    public BaseResponse<?> deleteOrder(@RequestBody Long id){
        orderService.deleteById(id);
        return BaseResponse.ok();
    }
}
