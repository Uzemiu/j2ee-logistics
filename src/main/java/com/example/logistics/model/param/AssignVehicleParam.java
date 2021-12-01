package com.example.logistics.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AssignVehicleParam {

    @NotNull
    private Long orderId;

    private Long vehicleId;
}
