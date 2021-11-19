package com.example.logistics.service.impl;

import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.dto.VehicleDTO;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.entity.Vehicle;
import com.example.logistics.model.query.VehicleQuery;
import com.example.logistics.repository.VehicleRepository;
import com.example.logistics.service.EmployeeService;
import com.example.logistics.service.VehicleService;
import com.example.logistics.service.base.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service("vehicleServiceImpl")
public class VehicleServiceImpl extends AbstractCrudService<Vehicle, Long> implements VehicleService {

    private final VehicleRepository vehicleRepository;

    private final EmployeeService employeeService;

    protected VehicleServiceImpl(VehicleRepository repository,
                                 EmployeeService employeeService) {
        super(repository);
        this.vehicleRepository = repository;
        this.employeeService = employeeService;
    }

    @Override
    public Page<Vehicle> queryBy(VehicleQuery query, Pageable pageable) {
        return vehicleRepository.findAll(query.toSpecification(), pageable);
    }

    @Override
    public long countByDriver(Employee driver) {
        return vehicleRepository.countByDriver(driver);
    }

    @Override
    public VehicleDTO toDto(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();
        BeanUtils.copyProperties(vehicle, dto);
        dto.setDriver(employeeService.toDto(vehicle.getDriver()));
        return dto;
    }

    @Transactional
    @Override
    public void doTest() {
        Vehicle vehicle = vehicleRepository.findByIdPessimistic(1L).get();
        log.info(Thread.currentThread().getName() + " select: " + vehicle);
        vehicle.setVehicleNumber(String.valueOf(Math.random()));
        vehicleRepository.save(vehicle);
        log.info(Thread.currentThread().getName() + " save: " + vehicle);
    }
}
