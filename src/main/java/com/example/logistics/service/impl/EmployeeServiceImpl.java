package com.example.logistics.service.impl;

import com.example.logistics.model.entity.Employee;
import com.example.logistics.repository.EmployeeRepository;
import com.example.logistics.repository.UserRepository;
import com.example.logistics.service.EmployeeService;
import com.example.logistics.service.base.AbstractUserService;
import org.springframework.stereotype.Service;

@Service("employeeService")
public class EmployeeServiceImpl extends AbstractUserService<Employee> implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    protected EmployeeServiceImpl(EmployeeRepository repository) {
        super(repository);
        this.employeeRepository = repository;
    }
}
