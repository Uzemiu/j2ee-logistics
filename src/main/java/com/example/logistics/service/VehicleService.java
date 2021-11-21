package com.example.logistics.service;

import com.example.logistics.model.dto.VehicleDTO;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.entity.Order;
import com.example.logistics.model.entity.Vehicle;
import com.example.logistics.model.query.VehicleQuery;
import com.example.logistics.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VehicleService extends CrudService<Vehicle, Long> {

    Page<Vehicle> queryBy(VehicleQuery query, Pageable pageable);

    long countByDriver(Employee driver);

    VehicleDTO toDto(Vehicle vehicle);

    void doTest();
}
