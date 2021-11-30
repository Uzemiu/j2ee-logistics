package com.example.logistics.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.example.logistics.exception.BadRequestException;
import com.example.logistics.model.dto.ClientInfoDTO;
import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.param.LoginParam;
import com.example.logistics.model.param.RegisterParam;
import com.example.logistics.repository.ClientRepository;
import com.example.logistics.service.ClientService;
import com.example.logistics.service.base.AbstractCrudService;
import com.example.logistics.service.base.AbstractUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Service("clientService")
public class ClientServiceImpl extends AbstractUserService<Client> implements ClientService {

    private final ClientRepository clientRepository;

    protected ClientServiceImpl(ClientRepository repository) {
        super(repository);
        this.clientRepository = repository;
    }

    @Override
    public Client register(RegisterParam param) {
        checkUniqueColumn(param.getUsername(), param.getEmail(), param.getPhoneNumber());
        Client client = new Client();
        BeanUtils.copyProperties(param, client);
        String encryptPassword = BCrypt.hashpw(param.getPassword(), BCrypt.gensalt());
        client.setPassword(encryptPassword);
        return clientRepository.save(client);
    }

    @Override
    public Client update(ClientInfoDTO client) {
        Client old = getNotNullById(client.getId());
        // 不更新密码 邮箱
        Client copy = new Client();
        BeanUtils.copyProperties(client, copy);
        copy.setPassword(old.getPassword());
        copy.setEmail(old.getEmail());
        try{
            return clientRepository.save(old);
        } catch (DataIntegrityViolationException e){
            Throwable t = e.getCause().getCause();
            if(t instanceof SQLIntegrityConstraintViolationException){
                throw new BadRequestException(t.getMessage());
            }
            throw new BadRequestException(e.getMessage());
        }
    }
}
