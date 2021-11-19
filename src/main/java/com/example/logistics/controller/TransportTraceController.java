package com.example.logistics.controller;


import com.example.logistics.annotation.Permission;
import com.example.logistics.model.entity.Order;
import com.example.logistics.model.entity.TransportTrace;
import com.example.logistics.model.enums.OrderStatus;
import com.example.logistics.model.enums.Role;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.repository.TransportTraceRepository;
import com.example.logistics.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/trace")
@RequiredArgsConstructor
public class TransportTraceController {

    private final TransportTraceRepository transportTraceRepository;

    private final OrderService orderService;

    @Permission(allowRoles = Role.ADMIN)
    @PostMapping("random")
    public BaseResponse<?> generateRandomTraces(@RequestBody Long id){
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
        if(order.getStatus().lessThan(OrderStatus.RECEIPT_CONFIRMED)){
            order.setStatus(orderStatuses[traceStatus - 1]);
        }
        orderService.update(order);
        transportTraceRepository.saveAll(traces);

        return BaseResponse.ok();
    }
}
