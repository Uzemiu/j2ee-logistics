package com.example.logistics.service.impl;

import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.dto.VehicleDTO;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.entity.Order;
import com.example.logistics.model.entity.Vehicle;
import com.example.logistics.model.enums.Role;
import com.example.logistics.model.enums.VehicleStatus;
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
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service("vehicleService")
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
    public Vehicle create(Vehicle vehicle) {
        Employee driver = vehicle.getDriver();
        if(driver != null){
            driver = employeeService.getNotNullById(driver.getId()); // 司机必须存在
            Assert.isTrue(driver.getRole().equals(Role.DRIVER), "司机角色不符");
            Assert.isTrue(countByDriver(driver) == 0, "一位司机只能被分配到一辆车"); // TODO: 11/20/2021 一辆车?
        }
        vehicle.setStatus(VehicleStatus.IDLE);
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        Vehicle old = getNotNullById(vehicle.getId());
        Employee driver = vehicle.getDriver();
        if(driver != null){
            driver = employeeService.getNotNullById(driver.getId()); // 司机必须存在
            Assert.isTrue(driver.getRole().equals(Role.DRIVER), "司机角色不符");
            if(!driver.equals(old.getDriver())){
                Assert.isTrue(countByDriver(driver) == 0, "一位司机只能被分配到一辆车");
            }
        }
        old.setVehicleNumber(vehicle.getVehicleNumber());
//        old.setStatus(vehicle.getStatus());
        // TODO: 11/20/2021 是否可以直接更新状态
        old.setDriver(driver);
        vehicleRepository.save(old);
        return old;
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

}
