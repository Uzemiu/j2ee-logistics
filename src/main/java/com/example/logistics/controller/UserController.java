package com.example.logistics.controller;

import com.example.logistics.annotation.AnonymousAccess;
import com.example.logistics.annotation.Permission;
import com.example.logistics.model.dto.EmployeeDTO;
import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.service.ClientService;
import com.example.logistics.service.EmployeeService;
import com.example.logistics.service.UserService;
import com.example.logistics.utils.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final ClientService clientService;

    private final EmployeeService employeeService;

    @ApiOperation(value = "获取登录用户信息", notes = "未登录返回null")
    @AnonymousAccess
    @GetMapping("info")
    public BaseResponse<? extends UserDTO> currentUserInfo(){
        UserService<? extends User> userService =  SecurityUtil.isClient() ? clientService : employeeService;
        return BaseResponse.ok("ok", userService.toDto(SecurityUtil.getCurrentUser()));
    }

    @ApiOperation(value = "退出登录")
    @AnonymousAccess
    @PostMapping("logout")
    public BaseResponse<?> logout(@ApiIgnore HttpSession session){
        session.removeAttribute("user");
        return BaseResponse.ok();
    }
}
