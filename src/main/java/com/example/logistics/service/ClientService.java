package com.example.logistics.service;

import com.example.logistics.model.dto.UserInfoDTO;
import com.example.logistics.model.entity.Client;
import com.example.logistics.model.param.RegisterParam;

public interface ClientService extends UserService<Client> {

    /**
     *
     * @param param
     * @return 注册成功返回存入数据库中的用户信息
     * @throws IllegalArgumentException 登录失败
     */
    Client register(RegisterParam param);

    Client update(UserInfoDTO client);

}
