package com.example.logistics.model.dto;

import com.example.logistics.model.entity.TransportTrace;
import com.example.logistics.model.entity.Vehicle;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailDTO extends OrderDTO{

    // for admin
    private VehicleDTO transportVehicle;
}
