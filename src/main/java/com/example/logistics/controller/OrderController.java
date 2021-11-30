package com.example.logistics.controller;

import com.alipay.api.AlipayApiException;
import com.example.logistics.annotation.Permission;
import com.example.logistics.exception.BadRequestException;
import com.example.logistics.model.dto.OrderDTO;
import com.example.logistics.model.dto.OrderDetailDTO;
import com.example.logistics.model.dto.PageDTO;
import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.*;
import com.example.logistics.model.enums.OrderStatus;
import com.example.logistics.model.enums.Role;
import com.example.logistics.model.query.OrderQuery;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.repository.TransportTraceRepository;
import com.example.logistics.service.AlipayService;
import com.example.logistics.service.OrderService;
import com.example.logistics.utils.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final AlipayService alipayService;

    private final TransportTraceRepository transportTraceRepository;

    @ApiOperation(value = "查询当前用户订单", notes = "仅登录用户可调用")
    @Permission(allowClient = true)
    @GetMapping("/mine")
    public BaseResponse<PageDTO<OrderDTO>> listOrderByCurrentUser(OrderQuery query,
                                                                  @PageableDefault(sort = {"createTime"},
                                                          direction = Sort.Direction.DESC) Pageable pageable){
        query.setSenderId(SecurityUtil.getCurrentUser().getId());
        Page<Order> orders = orderService.queryBy(query, pageable);
        return BaseResponse.ok("ok", new PageDTO<>(orders.map(orderService::toDto)));
    }

    @ApiOperation(value = "用户创建订单", notes = "仅登录用户可调用")
    @Permission(allowClient = true)
    @PostMapping
    public BaseResponse<Long> createOrder(@RequestBody @Validated Order order){
        order.setSender((Client) SecurityUtil.getCurrentUser());
        order.setStatus(OrderStatus.ORDER_CREATED);
        order.setTransportVehicle(null);
        order.setPrice(BigDecimal.valueOf(Math.random() * 100));
        Order res = orderService.create(order);
        return BaseResponse.ok("ok", res.getId());
    }

    @ApiOperation(value = "用户支付订单", notes = "仅登录用户可调用")
    @Permission(allowClient = true)
    @PostMapping("pay")
    public BaseResponse<String> payOrder(@RequestBody Long id){
        return BaseResponse.ok("ok", orderService.payOrder(id));
    }

    @ApiOperation(value = "用户确认收货订单", notes = "仅登录用户可调用")
    @Permission(allowClient = true)
    @PostMapping("confirm")
    public BaseResponse<?> confirmOrder(@RequestBody Long id){
        orderService.confirmOrder(id);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "取消订单", notes = "仅登录用户和管理员可调用")
    @Permission(allowClient = true, allowRoles = Role.ADMIN)
    @PostMapping("cancel")
    public BaseResponse<?> cancelOrder(@RequestBody Long id){
        orderService.cancelOrder(id);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "更新订单信息", notes = "仅登录用户和管理员可调用")
    @Permission(allowClient = true, allowRoles = Role.ADMIN)
    @PutMapping
    public BaseResponse<?> updateOrder(@RequestBody @Validated Order order){
        orderService.update(order);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "管理员查看订单", notes = "仅管理员可调用")
    @Permission(allowRoles = Role.ADMIN)
    @GetMapping("/list")
    public BaseResponse<PageDTO<OrderDetailDTO>> listOrder(OrderQuery query,
                                                           @PageableDefault(sort = {"createTime"},
                                             direction = Sort.Direction.DESC) Pageable pageable){
        Page<Order> orders = orderService.queryBy(query, pageable);
        // 管理员能查询到车辆分配信息
        return BaseResponse.ok("ok", new PageDTO<>(orders.map(orderService::toDetailDto)));
    }


    @Permission(allowRoles = Role.ADMIN)
    public BaseResponse<?> assignIdleVehicle(){
        // TODO: 11/20/2021
        return BaseResponse.ok();
    }

    @ApiOperation(value = "删除订单", notes = "仅管理员可调用")
    @Permission(allowRoles = Role.ADMIN)
    @DeleteMapping
    public BaseResponse<?> deleteOrder(@RequestBody Long id){
        // TODO: 11/20/2021  管理员能否删除订单
        orderService.deleteById(id);
        return BaseResponse.ok();
    }
}
