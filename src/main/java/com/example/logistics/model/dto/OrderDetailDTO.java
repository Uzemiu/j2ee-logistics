package com.example.logistics.model.dto;

import com.example.logistics.model.entity.TransportTrace;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailDTO {

    private OrderDTO order;

    private List<TransportTraceDTO> traces;
}
