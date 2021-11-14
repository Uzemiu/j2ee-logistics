package com.example.logistics.service.impl;

import com.example.logistics.model.dto.EmployeeDTO;
import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.repository.EmployeeRepository;
import com.example.logistics.service.EmployeeService;
import com.example.logistics.service.base.AbstractUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("employeeService")
public class EmployeeServiceImpl extends AbstractUserService<Employee> implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    protected EmployeeServiceImpl(EmployeeRepository repository) {
        super(repository);
        this.employeeRepository = repository;
    }

    @Override
    public UserDTO toDto(Employee employee) {
        if(employee == null){
            return null;
        }
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, dto);
        return dto;
    }

    @Override
    public Employee create(Employee employee) {
        checkUniqueColumn(employee.getUsername(), employee.getEmail(), employee.getPhoneNumber());
        return super.create(employee);
    }

    @Override
    public Employee update(Employee employee) {
        Employee old = getNotNullById(employee.getId());
        if(!old.getUsername().equals(employee.getUsername())){
            checkUniqueUsername(employee.getUsername());
        }
        if(!old.getEmail().equals(employee.getEmail())){
            checkUniqueEmail(employee.getEmail());
        }
        if(!old.getPhoneNumber().equals(employee.getPhoneNumber())){
            checkUniquePhone(employee.getPhoneNumber());
        }
        // 不更新密码
        employee.setPassword(old.getPassword());
        return super.update(employee);
    }
}
