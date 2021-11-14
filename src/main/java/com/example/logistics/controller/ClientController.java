package com.example.logistics.controller;

import com.example.logistics.annotation.AnonymousAccess;
import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.Client;
import com.example.logistics.model.param.LoginParam;
import com.example.logistics.model.param.RegisterParam;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @AnonymousAccess
    @PostMapping("register")
    public BaseResponse<?> register(@RequestBody @Validated RegisterParam param, HttpSession session){
        Client client = clientService.register(param);
        session.setAttribute("user", client);
        return BaseResponse.ok("ok", clientService.toDto(client));
    }

    @AnonymousAccess
    @PostMapping("login")
    public BaseResponse<?> login(@RequestBody @Validated LoginParam param, HttpSession session){
        Client client = clientService.login(param);
        session.setAttribute("user", client);
        return BaseResponse.ok("ok", clientService.toDto(client));
    }

}
