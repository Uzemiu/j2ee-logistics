package com.example.logistics.model.dto;

import com.example.logistics.model.entity.Order;
import com.example.logistics.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportTraceDTO {

    private Long id;

    private String information;

    private OrderStatus orderStatus;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date traceTime;
}
