package com.example.logistics.model.dto;

import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.enums.VehicleStatus;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
public class VehicleDTO {

    private Long id;

    private String vehicleNumber;

    private VehicleStatus status;

    private UserDTO driver;

}
