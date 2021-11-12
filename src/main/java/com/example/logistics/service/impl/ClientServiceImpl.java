package com.example.logistics.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.example.logistics.exception.BadRequestException;
import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.param.LoginParam;
import com.example.logistics.model.param.RegisterParam;
import com.example.logistics.repository.ClientRepository;
import com.example.logistics.service.ClientService;
import com.example.logistics.service.base.AbstractCrudService;
import com.example.logistics.service.base.AbstractUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("clientService")
public class ClientServiceImpl extends AbstractUserService<Client> implements ClientService {

    private final ClientRepository clientRepository;

    protected ClientServiceImpl(ClientRepository repository) {
        super(repository);
        this.clientRepository = repository;
    }

    @Override
    public Client register(RegisterParam param) {
        Assert.isTrue(clientRepository.countByUsername(param.getUsername()) == 0, "该用户名已被注册");
        Assert.isTrue(clientRepository.countByEmail(param.getEmail()) == 0,"该邮箱已被注册");
        Assert.isTrue(clientRepository.countByPhoneNumber(param.getPhoneNumber()) == 0, "该手机号已被注册");

        Client client = new Client();
        BeanUtils.copyProperties(param, client);
        String encryptPassword = BCrypt.hashpw(param.getPassword(), BCrypt.gensalt());
        client.setPassword(encryptPassword);
        return clientRepository.save(client);
    }

    @Override
    public Client login(LoginParam param) {
        Client client = clientRepository.findByUsername(param.getUsername())
                .orElseThrow(() -> new BadRequestException("用户名或密码错误"));
        Assert.isTrue(BCrypt.checkpw(param.getPassword(), client.getPassword()),"用户名或密码错误");
        return client;
    }
}
