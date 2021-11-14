package com.example.logistics.controller;

import cn.hutool.crypto.digest.BCrypt;
import com.example.logistics.annotation.AnonymousAccess;
import com.example.logistics.annotation.Permission;
import com.example.logistics.model.dto.PageDTO;
import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.enums.Role;
import com.example.logistics.model.param.LoginParam;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.service.EmployeeService;
import com.example.logistics.utils.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @AnonymousAccess
    @PostMapping("login")
    public BaseResponse<?> login(@RequestBody @Validated LoginParam param, HttpSession session){
        Employee employee = employeeService.login(param);
        session.setAttribute("user", employee);
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(employee, dto);
        return BaseResponse.ok("ok", dto);
    }

    @Permission(allowRoles = Role.ADMIN)
    @GetMapping
    public BaseResponse<?> listEmployee(@PageableDefault(
            sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable){
        Page<UserDTO> userDTOS = employeeService.query(pageable).map(employeeService::toDto);
        return BaseResponse.ok("ok", new PageDTO<>(userDTOS));
    }

    @Permission(allowRoles = Role.ADMIN)
    @PostMapping
    public BaseResponse<?> addEmployee(@RequestBody @Validated Employee employee){
        employee.setPassword(BCrypt.hashpw(employee.getPassword()));
        employeeService.create(employee);
        return BaseResponse.ok();
    }

    @Permission(allowRoles = Role.ADMIN)
    @PutMapping
    public BaseResponse<?> updateEmployee(@RequestBody @Validated Employee employee){
        employeeService.update(employee);
        return BaseResponse.ok();
    }

    @Permission(allowRoles = Role.ADMIN)
    @DeleteMapping
    public BaseResponse<?> deleteEmployee(@RequestBody Long id){
        employeeService.deleteById(id);
        return BaseResponse.ok();
    }
}
