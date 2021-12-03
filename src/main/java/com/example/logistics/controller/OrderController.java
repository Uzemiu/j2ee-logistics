package com.example.logistics.controller;

import com.example.logistics.annotation.Permission;
import com.example.logistics.model.dto.OrderDTO;
import com.example.logistics.model.dto.OrderDetailDTO;
import com.example.logistics.model.dto.PageDTO;
import com.example.logistics.model.entity.*;
import com.example.logistics.model.enums.OrderStatus;
import com.example.logistics.model.enums.Role;
import com.example.logistics.model.param.AssignVehicleParam;
import com.example.logistics.model.param.CreateOrderParam;
import com.example.logistics.model.query.OrderQuery;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.repository.TransportTraceRepository;
import com.example.logistics.service.AlipayService;
import com.example.logistics.service.OrderService;
import com.example.logistics.utils.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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

    @ApiOperation(value = "根据车辆ID查询订单", notes = "仅登录用户可调用")
    @Permission(allowRoles = Role.ADMIN)
    @GetMapping("/vehicle")
    public BaseResponse<OrderDTO> getOrderByVehicleId(Long id){
        return BaseResponse.ok("ok", orderService.toDto(orderService.getOrderByVehicleId(id).orElse(null)));
    }

    @ApiOperation(value = "查询当前用户发货订单", notes = "仅登录用户可调用")
    @Permission(allowClient = true)
    @GetMapping("/mine")
    public BaseResponse<PageDTO<OrderDTO>> listSendingOrderByCurrentUser(OrderQuery query,
                                                                         @PageableDefault(sort = {"createTime"},
                                                                                 direction = Sort.Direction.DESC) Pageable pageable){
        query.setSenderId(SecurityUtil.getCurrentUser().getId());
        Page<Order> orders = orderService.queryBy(query, pageable);
        return BaseResponse.ok("ok", new PageDTO<>(orders.map(orderService::toDto)));
    }

    @ApiOperation(value = "查询当前用户收货订单", notes = "仅登录用户可调用")
    @Permission(allowClient = true)
    @GetMapping("/receive")
    public BaseResponse<PageDTO<OrderDTO>> listReceivingOrderByCurrentUser(OrderQuery query,
                                                                           @PageableDefault(sort = {"createTime"},
                                                                                   direction = Sort.Direction.DESC) Pageable pageable){
        query.setSenderId(null);
        query.setReceiverPhone(SecurityUtil.getCurrentUser().getPhoneNumber());
        Page<Order> orders = orderService.queryBy(query, pageable);
        return BaseResponse.ok("ok", new PageDTO<>(orders.map(orderService::toDto)));
    }


    @ApiOperation(value = "用户创建订单", notes = "仅登录用户可调用")
    @Permission(allowClient = true)
    @PostMapping
    public BaseResponse<Long> createOrder(@RequestBody @Validated CreateOrderParam param){
        param.setId(null);
        Order order = new Order();
        BeanUtils.copyProperties(param, order);
        order.setPayed(false);
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
    public BaseResponse<?> updateOrder(@RequestBody @Validated CreateOrderParam param){
        Order order = new Order();
        BeanUtils.copyProperties(param, order);
        // no update vehicle
        orderService.update(order);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "分配车辆", notes = "仅管理员可调用")
    @Permission(allowRoles = Role.ADMIN)
    @PostMapping("vehicle")
    public BaseResponse<?> assignVehicle(@RequestBody @Validated AssignVehicleParam param){
        orderService.assignVehicle(param.getOrderId(), param.getVehicleId());
        return BaseResponse.ok();
    }

    @ApiOperation(value = "管理员查看订单", notes = "仅管理员可调用")
    @Permission(allowRoles = Role.ADMIN)
    @GetMapping("list")
    public BaseResponse<PageDTO<OrderDetailDTO>> listOrder(OrderQuery query,
                                                           @PageableDefault(sort = {"createTime"},
                                                                   direction = Sort.Direction.DESC) Pageable pageable){
        Page<Order> orders = orderService.queryBy(query, pageable);
        // 管理员能查询到车辆分配信息
        return BaseResponse.ok("ok", new PageDTO<>(orders.map(orderService::toDetailDto)));
    }

}
