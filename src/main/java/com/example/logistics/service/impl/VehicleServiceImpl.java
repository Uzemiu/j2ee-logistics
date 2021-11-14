package com.example.logistics.service.impl;

import com.example.logistics.model.entity.Vehicle;
import com.example.logistics.repository.BaseRepository;
import com.example.logistics.repository.VehicleRepository;
import com.example.logistics.service.VehicleService;
import com.example.logistics.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service("vehicleServiceImpl")
public class VehicleServiceImpl extends AbstractCrudService<Vehicle, Long> implements VehicleService {

    private final VehicleRepository vehicleRepository;

    protected VehicleServiceImpl(VehicleRepository repository) {
        super(repository);
        this.vehicleRepository = repository;
    }
}
