package com.example.logistics.controller;

import com.example.logistics.annotation.Permission;
import com.example.logistics.model.dto.ClientRateDTO;
import com.example.logistics.model.entity.ClientRate;
import com.example.logistics.model.entity.Order;
import com.example.logistics.model.enums.Role;
import com.example.logistics.model.param.ClientRateParam;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.service.ClientRateService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rate")
@RequiredArgsConstructor
public class ClientRateController {

    private final ClientRateService clientRateService;

    @ApiOperation(value = "查询订单评分信息", notes = "仅登录用户和管理员可查看")
    @Permission(allowClient = true, allowRoles = Role.ADMIN)
    @GetMapping
    public BaseResponse<ClientRateDTO> getByOrderId(@RequestBody Long id){
        Order order = new Order();
        order.setId(id);
        ClientRate rate = clientRateService.getByOrder(order).orElse(null);
        return BaseResponse.ok("ok", clientRateService.toDto(rate));
    }

    @ApiOperation(value = "用户评价订单", notes = "仅登录用户可查看")
    @Permission(allowClient = true)
    @PostMapping
    public BaseResponse<?> rateOrder(@Validated @RequestBody ClientRateParam rate){
        clientRateService.rateOrder(rate);
        return BaseResponse.ok();
    }
}
