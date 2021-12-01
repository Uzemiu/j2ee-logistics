package com.example.logistics.service;

import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.param.ResetPasswordParam;
import com.example.logistics.model.query.EmployeeQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService extends UserService<Employee>{

    Employee resetPasswordAdmin(Long employeeId);

}
