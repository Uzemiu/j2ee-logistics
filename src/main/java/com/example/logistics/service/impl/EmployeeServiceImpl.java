package com.example.logistics.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.example.logistics.model.dto.EmployeeDTO;
import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.entity.User;
import com.example.logistics.repository.EmployeeRepository;
import com.example.logistics.repository.VehicleRepository;
import com.example.logistics.service.EmployeeService;
import com.example.logistics.service.base.AbstractUserService;
import com.example.logistics.utils.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@Service("employeeService")
public class EmployeeServiceImpl extends AbstractUserService<Employee> implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final VehicleRepository vehicleRepository;

    protected EmployeeServiceImpl(EmployeeRepository repository,
                                  VehicleRepository vehicleRepository) {
        super(repository);
        this.employeeRepository = repository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Employee resetPasswordAdmin(Long employeeId) {
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
        old.setGender(employee.getGender());
        // 不更新密码
        return super.update(old);
    }

    @Transactional
    @Override
    public Employee deleteById(Long id) {
        User user = SecurityUtil.getCurrentUser();
        Assert.isTrue(!user.getId().equals(id), "不能删除自己");

        Employee employee = new Employee();
        employee.setId(id);
        Assert.isTrue(vehicleRepository.countByDriver(employee) == 0, "当前司机已经被分配车辆");
//        vehicleRepository.setDriverToNull(employee);

        return super.deleteById(id);
    }
}
