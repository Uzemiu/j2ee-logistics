package com.example.logistics.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.example.logistics.model.dto.EmployeeDTO;
import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.entity.User;
import com.example.logistics.repository.EmployeeRepository;
import com.example.logistics.service.EmployeeService;
import com.example.logistics.service.base.AbstractUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service("employeeService")
public class EmployeeServiceImpl extends AbstractUserService<Employee> implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    protected EmployeeServiceImpl(EmployeeRepository repository) {
        super(repository);
        this.employeeRepository = repository;
    }

    @Override
    public Employee resetPassword(Long employeeId) {
        // TODO: 2021/11/23 更改密码后应该强制下线
        Employee employee = getNotNullById(employeeId);
        employee.setPassword(BCrypt.hashpw("ab1234"));
        return employeeRepository.save(employee);
    }

    @Override
    public UserDTO toDto(User employee) {
        if(employee == null){
            return null;
        }
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, dto);
        return dto;
    }

    /**
     *
     * @param employee password不用加密
     * @return
     */
    @Override
    public Employee create(Employee employee) {
        checkUniqueColumn(employee.getUsername(), employee.getEmail(), employee.getPhoneNumber());
        employee.setPassword(BCrypt.hashpw(employee.getPassword()));
        return super.create(employee);
    }

    /**
     * 不更新密码
     * @param employee
     * @return
     */
    @Override
    public Employee update(Employee employee) {
        Employee old = getNotNullById(employee.getId());
        if(!old.getUsername().equals(employee.getUsername())){
            checkUniqueUsername(employee.getUsername());
            old.setUsername(employee.getUsername());
        }
        if(!old.getEmail().equals(employee.getEmail())){
            checkUniqueEmail(employee.getEmail());
            old.setEmail(employee.getEmail());
        }
        if(!old.getPhoneNumber().equals(employee.getPhoneNumber())){
            checkUniquePhone(employee.getPhoneNumber());
            old.setPhoneNumber(employee.getPhoneNumber());
        }
        old.setRealName(employee.getRealName());
        old.setRole(employee.getRole());
        // 不更新密码
        return super.update(old);
    }
}
