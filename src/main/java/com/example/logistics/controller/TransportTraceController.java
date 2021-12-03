package com.example.logistics.controller;


import com.example.logistics.annotation.Permission;
import com.example.logistics.model.dto.TransportTraceDTO;
import com.example.logistics.model.entity.Order;
import com.example.logistics.model.entity.TransportTrace;
import com.example.logistics.model.entity.Vehicle;
import com.example.logistics.model.enums.OrderStatus;
import com.example.logistics.model.enums.Role;
import com.example.logistics.model.enums.VehicleStatus;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.repository.TransportTraceRepository;
import com.example.logistics.repository.VehicleRepository;
import com.example.logistics.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trace")
@RequiredArgsConstructor
public class TransportTraceController {

    private final TransportTraceRepository transportTraceRepository;

    private final VehicleRepository vehicleRepository;

    private final OrderService orderService;

    @ApiOperation(value = "查询订单物流详细信息", notes = "登录用户和管理员可调用")
    @Permission(allowClient = true, allowRoles = Role.ADMIN)
    @GetMapping
    public BaseResponse<List<TransportTraceDTO>> listByOrderId(Long id){
        Order order = new Order();
        order.setId(id);
        List<TransportTraceDTO> traces = transportTraceRepository.findByOrder(order).stream().map(this::toDto).collect(Collectors.toList());
        return BaseResponse.ok("ok", traces);
    }

    @ApiOperation(value = "随机生成物流记录", notes = "仅管理员可调用")
    @Permission(allowRoles = Role.ADMIN)
    @PostMapping("random")
    public BaseResponse<List<TransportTraceDTO>> generateRandomTraces(@RequestBody Long id){
        Order order = orderService.getNotNullById(id);

        List<TransportTrace> traces = new ArrayList<>();
        Random random = new Random();
        long randomEndTime = System.currentTimeMillis() + (random.nextInt(36) + 36) * 3600_000;
        OrderStatus[] orderStatuses = OrderStatus.class.getEnumConstants();
        int endStatus = OrderStatus.RECEIPT_CONFIRMED.ordinal(); // 随机生成状态到确认收货（不包括）

        int traceStatus = order.getStatus().ordinal() + 1;
        // 以分钟为单位
        long traceTime = System.currentTimeMillis() + (random.nextInt(18 * 60) + 60) * 60_000;
        for(;traceTime < randomEndTime && traceStatus < endStatus;
            traceTime += (random.nextInt(18 * 60) + 60) * 60_000, traceStatus++){
            TransportTrace trace = new TransportTrace();
            trace.setTraceTime(new Date(traceTime));
            trace.setOrder(order);
            trace.setOrderStatus(orderStatuses[traceStatus]);
            traces.add(trace);
        }
        transportTraceRepository.saveAll(traces);

        if(order.getStatus().lessThan(OrderStatus.RECEIPT_CONFIRMED)){
            // 用户没有提前确认收货
            order.setStatus(orderStatuses[traceStatus - 1]);
            orderService.update(order);
        }

        if(traceStatus == endStatus - 1){
            // 模拟送货完成，设置车辆状态
            Vehicle vehicle = order.getTransportVehicle();
            if(vehicle != null){
                vehicle.setStatus(VehicleStatus.IDLE);
                vehicleRepository.save(vehicle);
            }
        }

        List<TransportTraceDTO> traceDtos = traces.stream().map(this::toDto).collect(Collectors.toList());
        return BaseResponse.ok("ok", traceDtos);
    }


    private TransportTraceDTO toDto(TransportTrace trace){
        TransportTraceDTO dto = new TransportTraceDTO();
        BeanUtils.copyProperties(trace, dto);
        return dto;
    }
}
