package com.example.logistics.service.base;

import cn.hutool.crypto.digest.BCrypt;
import com.example.logistics.exception.BadRequestException;
import com.example.logistics.model.dto.PageDTO;
import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.param.LoginParam;
import com.example.logistics.repository.BaseRepository;
import com.example.logistics.repository.UserRepository;
import com.example.logistics.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.List;

public abstract class AbstractUserService<USER extends User> extends AbstractCrudService<USER, Long> implements UserService<USER> {

    private final UserRepository<USER> userRepository;

    protected AbstractUserService(UserRepository<USER> repository) {
        super(repository);
        this.userRepository = repository;
    }

    @Override
    public USER login(LoginParam param) {
        USER user = userRepository.findByUsername(param.getUsername())
                .orElseThrow(() -> new BadRequestException("用户名或密码错误"));
        Assert.isTrue(BCrypt.checkpw(param.getPassword(), user.getPassword()),"用户名或密码错误");
        return user;
    }

    @Override
    public UserDTO toDto(USER user) {
        if(user == null){
            return null;
        }
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    @Override
    public Page<USER> query(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    @Override
    public void checkUniqueUsername(String username) {
        Assert.isTrue(userRepository.countByUsername(username) == 0, "该用户名已被注册");
    }

    @Override
    public void checkUniqueEmail(String email) {
        Assert.isTrue(userRepository.countByEmail(email) == 0,"该邮箱已被注册");
    }

    @Override
    public void checkUniquePhone(String phone) {
        Assert.isTrue(userRepository.countByPhoneNumber(phone) == 0, "该手机号已被注册");
    }

    @Override
    public void checkUniqueColumn(String username, String email, String phoneNumber) {
        checkUniqueUsername(username);
        checkUniqueEmail(email);
        checkUniquePhone(phoneNumber);
    }
}
