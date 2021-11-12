package com.example.logistics.service;

import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.param.LoginParam;
import com.example.logistics.model.param.RegisterParam;
import com.example.logistics.service.base.CrudService;

public interface ClientService extends UserService<Client> {

    /**
     *
     * @param param
     * @return 注册成功返回存入数据库中的用户信息
     * @throws IllegalArgumentException 登录失败
     */
    Client register(RegisterParam param);

    /**
     *
     * @param param
     * @return 登陆成功返回登录用户信息
     * @throws IllegalArgumentException 登录失败
     */
    Client login(LoginParam param);

}
