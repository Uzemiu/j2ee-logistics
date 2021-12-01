package com.example.logistics.controller;

import com.example.logistics.annotation.AnonymousAccess;
import com.example.logistics.annotation.Permission;
import com.example.logistics.model.dto.EmployeeDTO;
import com.example.logistics.model.dto.PageDTO;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.enums.Role;
import com.example.logistics.model.param.AddEmployeeParam;
import com.example.logistics.model.param.LoginParam;
import com.example.logistics.model.param.ResetPasswordParam;
import com.example.logistics.model.query.EmployeeQuery;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.repository.EmployeeRepository;
import com.example.logistics.repository.VehicleRepository;
import com.example.logistics.service.EmployeeService;
import com.example.logistics.utils.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final VehicleRepository vehicleRepository;

    @ApiOperation(value = "员工登录", notes = "默认用户名密码admin")
    @AnonymousAccess
    @PostMapping("login")
    public BaseResponse<EmployeeDTO> login(@RequestBody @Validated LoginParam param, @ApiIgnore HttpSession session){
        Employee employee = employeeService.login(param);
        session.setAttribute("user", employee);
        return BaseResponse.ok(session.getId(), (EmployeeDTO) employeeService.toDto(employee));
    }

    @ApiOperation(value = "员工重置密码", notes = "仅员工在登录状态下可调用")
    @Permission(allowEmployee = true)
    @PostMapping("reset-password")
    public BaseResponse<?> resetPassword(@RequestBody @Validated ResetPasswordParam param, @ApiIgnore HttpSession session){
        session.setAttribute("user",employeeService.resetPassword(param));
        return BaseResponse.ok();
    }

    @ApiOperation(value = "管理员查询员工信息", notes = "仅管理员可调用")
    @Permission(allowRoles = Role.ADMIN)
    @GetMapping
    public BaseResponse<PageDTO<EmployeeDTO>> listEmployee(EmployeeQuery query,
                                                           @PageableDefault(sort = {"createTime"},
                                                               direction = Sort.Direction.DESC) Pageable pageable){
        Page<EmployeeDTO> userDTOS = employeeService.query(query, pageable).map(u -> (EmployeeDTO)employeeService.toDto(u));
        return BaseResponse.ok("ok", new PageDTO<>(userDTOS));
    }

    @ApiOperation(value = "管理员添加员工", notes = "仅管理员可调用")
    @Permission(allowRoles = Role.ADMIN)
    @PostMapping
    public BaseResponse<Long> addEmployee(@RequestBody @Validated AddEmployeeParam param){
        Employee employee = new Employee();
        BeanUtils.copyProperties(param, employee);
        return BaseResponse.ok("ok",employeeService.create(employee).getId());
    }

    @ApiOperation(value = "管理员重置员工密码为ab1234", notes = "仅管理员可调用")
    @Permission(allowRoles = Role.ADMIN)
    @PostMapping("admin/reset-password")
    public BaseResponse<?> resetPasswordAdmin(@RequestBody Long id){
        employeeService.resetPasswordAdmin(id);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "管理员更新员工", notes = "仅管理员可调用")
    @Permission(allowRoles = Role.ADMIN)
    @PutMapping
    public BaseResponse<?> updateEmployee(@RequestBody @Validated AddEmployeeParam param){
        Employee employee = new Employee();
        BeanUtils.copyProperties(param, employee);
        employeeService.update(employee);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "管理员删除员工", notes = "仅管理员可调用")
    @Permission(allowRoles = Role.ADMIN)
    @DeleteMapping
    public BaseResponse<?> deleteEmployee(@RequestBody Long id){
        // TODO: 11/20/2021 判断员工是否有任务在身
        employeeService.deleteById(id);
        return BaseResponse.ok();
    }
}
