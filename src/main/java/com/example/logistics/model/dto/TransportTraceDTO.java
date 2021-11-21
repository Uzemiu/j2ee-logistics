package com.example.logistics.model.dto;

import com.example.logistics.model.entity.Order;
import com.example.logistics.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportTraceDTO {

    private Long id;

    private String information;

    private OrderStatus orderStatus;
}
