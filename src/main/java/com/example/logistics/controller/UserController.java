package com.example.logistics.controller;

import com.example.logistics.annotation.AnonymousAccess;
import com.example.logistics.annotation.Permission;
import com.example.logistics.model.dto.UserInfoDTO;
import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.param.ForgetPasswordParam;
import com.example.logistics.model.param.LoginParam;
import com.example.logistics.model.param.ResetEmailParam;
import com.example.logistics.model.param.ResetPasswordParam;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.service.CaptchaService;
import com.example.logistics.service.ClientService;
import com.example.logistics.service.EmployeeService;
import com.example.logistics.service.UserService;
import com.example.logistics.utils.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final ClientService clientService;

    private final EmployeeService employeeService;

    private final CaptchaService captchaService;

    @ApiOperation(value = "获取登录用户信息", notes = "未登录返回null")
    @AnonymousAccess
    @GetMapping("info")
    public BaseResponse<? extends UserDTO> currentUserInfo(){
        UserService<? extends User> userService =  SecurityUtil.isClient() ? clientService : employeeService;
        return BaseResponse.ok("ok", userService.toDto(SecurityUtil.getCurrentUser()));
    }

    @ApiOperation(value = "用户登录", notes = "默认用户名密码ab1234，需要验证码")
    @AnonymousAccess
    @PostMapping("login")
    public BaseResponse<UserDTO> login(@RequestBody @Validated LoginParam param, @ApiIgnore HttpSession session){
        captchaService.assertCaptcha(param);
        UserService<? extends User> userService =  SecurityUtil.isClient() ? clientService : employeeService;
        User user = userService.login(param);
        session.setAttribute("user", user);
        return BaseResponse.ok(session.getId(), userService.toDto(user));
    }

    @ApiOperation(value = "退出登录")
    @AnonymousAccess
    @PostMapping("logout")
    public BaseResponse<?> logout(@ApiIgnore HttpSession session){
        session.removeAttribute("user");
        return BaseResponse.ok();
    }

    @ApiOperation(value = "忘记密码", notes = "需要验证码")
    @AnonymousAccess
    @PostMapping("forget-password")
    public BaseResponse<?> forgetPassword(@RequestBody @Validated ForgetPasswordParam param, @ApiIgnore HttpSession session){
        captchaService.assertCaptcha(param);
        UserService<? extends User> userService =  SecurityUtil.isClient() ? clientService : employeeService;
        session.setAttribute("user", userService.forgetPassword(param));
        return BaseResponse.ok();
    }

    @ApiOperation(value = "重置邮箱", notes = "仅登录用户可调用，需要验证码")
    @Permission(allowClient = true, allowEmployee = true)
    @PostMapping("reset-email")
    public BaseResponse<?> resetEmail(@RequestBody @Validated ResetEmailParam param, @ApiIgnore HttpSession session){
        captchaService.assertCaptcha(param);
        UserService<? extends User> userService =  SecurityUtil.isClient() ? clientService : employeeService;
        session.setAttribute("user", userService.resetEmail(param));
        return BaseResponse.ok();
    }

    @ApiOperation(value = "重置密码", notes = "仅登录用户可调用，需要验证码")
    @Permission(allowClient = true, allowEmployee = true)
    @PostMapping("reset-password")
    public BaseResponse<?> resetPassword(@RequestBody @Validated ResetPasswordParam param, @ApiIgnore HttpSession session){
        captchaService.assertCaptcha(param);
        UserService<? extends User> userService =  SecurityUtil.isClient() ? clientService : employeeService;
        session.setAttribute("user", userService.resetPassword(param));
        return BaseResponse.ok();
    }

}
