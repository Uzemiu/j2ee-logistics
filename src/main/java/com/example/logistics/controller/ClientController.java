package com.example.logistics.controller;

import com.example.logistics.annotation.AnonymousAccess;
import com.example.logistics.annotation.Permission;
import com.example.logistics.model.dto.ClientInfoDTO;
import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.enums.Role;
import com.example.logistics.model.param.LoginParam;
import com.example.logistics.model.param.RegisterParam;
import com.example.logistics.model.param.ResetPasswordParam;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.service.ClientService;
import com.example.logistics.utils.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @AnonymousAccess
    @PostMapping("register")
    public BaseResponse<UserDTO> register(@RequestBody @Validated RegisterParam param, @ApiIgnore HttpSession session){
        Client client = clientService.register(param);
        session.setAttribute("user", client);
        return BaseResponse.ok("ok", clientService.toDto(client));
    }

    @ApiOperation(value = "用户登录", notes = "默认用户名密码ab1234")
    @AnonymousAccess
    @PostMapping("login")
    public BaseResponse<UserDTO> login(@RequestBody @Validated LoginParam param, @ApiIgnore HttpSession session){
        Client client = clientService.login(param);
        session.setAttribute("user", client);
        return BaseResponse.ok("ok", clientService.toDto(client));
    }

    @ApiOperation(value = "用户重置密码", notes = "仅在登录用户可调用")
    @Permission(allowClient = true)
    @PostMapping("reset-password")
    public BaseResponse<?> resetPassword(@RequestBody @Validated ResetPasswordParam param, @ApiIgnore HttpSession session){
        session.setAttribute("user", clientService.resetPassword(param));
        return BaseResponse.ok();
    }

    @ApiOperation(value = "用户更新个人信息", notes = "仅在登录用户可调用")
    @Permission(allowClient = true)
    @PutMapping
    public BaseResponse<?> updateInfo(@RequestBody @Validated ClientInfoDTO client, @ApiIgnore HttpSession session){
        User user = SecurityUtil.getCurrentUser();
        // 客户只能更新自己的信息
        client.setId(user.getId());
        clientService.update(client);
        return BaseResponse.ok();
    }
}
