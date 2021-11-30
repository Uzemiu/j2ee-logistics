package com.example.logistics.service;

import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.param.ResetPasswordParam;

public interface EmployeeService extends UserService<Employee>{

    Employee resetPassword(Long employeeId);

}
