package com.example.logistics.controller;

import com.example.logistics.annotation.AnonymousAccess;
import com.example.logistics.annotation.Permission;
import com.example.logistics.model.dto.EmployeeDTO;
import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.service.UserService;
import com.example.logistics.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @AnonymousAccess
    @GetMapping("info")
    public BaseResponse<?> currentUserInfo(){
        User user = SecurityUtil.getCurrentUser();
        UserDTO dto = new UserDTO();
        if(user != null){
            BeanUtils.copyProperties(user, dto);
        }
        return BaseResponse.ok("ok", dto);
    }

    @AnonymousAccess
    @PostMapping("logout")
    public BaseResponse<?> logout(HttpSession session){
        session.removeAttribute("user");
        return BaseResponse.ok();
    }
}
