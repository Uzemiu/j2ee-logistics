package com.example.logistics.controller;

import com.example.logistics.annotation.Permission;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.enums.Role;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @Permission(allowRoles = Role.ADMIN)
    @GetMapping
    public BaseResponse<?> listEmployee(@PageableDefault(
            sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable){

        return BaseResponse.ok();
    }

    @Permission(allowRoles = Role.ADMIN)
    @PostMapping
    public BaseResponse<?> addEmployee(Employee employee){

        return BaseResponse.ok();
    }

    @Permission(allowRoles = Role.ADMIN)
    @PutMapping
    public BaseResponse<?> updateEmployee(Employee employee){

        return BaseResponse.ok();
    }

    @Permission(allowRoles = Role.ADMIN)
    @DeleteMapping
    public BaseResponse<?> deleteEmployee(Long id){

        return BaseResponse.ok();
    }
}
