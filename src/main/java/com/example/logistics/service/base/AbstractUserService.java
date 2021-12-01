package com.example.logistics.service.base;

import cn.hutool.crypto.digest.BCrypt;
import com.example.logistics.exception.BadRequestException;
import com.example.logistics.exception.ResourceNotFoundException;
import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.dto.UserInfoDTO;
import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.param.ForgetPasswordParam;
import com.example.logistics.model.param.LoginParam;
import com.example.logistics.model.param.ResetEmailParam;
import com.example.logistics.model.param.ResetPasswordParam;
import com.example.logistics.model.query.UserQuery;
import com.example.logistics.repository.UserRepository;
import com.example.logistics.service.UserService;
import com.example.logistics.utils.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.sql.SQLIntegrityConstraintViolationException;

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
    public Page<USER> query(UserQuery<USER> query, Pageable pageable) {
        return userRepository.findAll(query.toSpecification(), pageable);
    }

    @Override
    public UserDTO toDto(User user) {
        if(user == null){
            return null;
        }
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
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

    @Override
    public USER resetPassword(ResetPasswordParam param) {
        User user = SecurityUtil.getCurrentUser();
        Assert.isTrue(BCrypt.checkpw(param.getOldPassword(), user.getPassword()), "原密码不符");
        user.setPassword(BCrypt.hashpw(param.getNewPassword()));
        return userRepository.save((USER) user);
    }

    @Override
    public USER forgetPassword(ForgetPasswordParam param) {
        USER user = userRepository.findByUsername(param.getUsername()).orElseThrow(() -> new ResourceNotFoundException("用户名不存在"));
        user.setPassword(BCrypt.hashpw(param.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public USER resetEmail(ResetEmailParam param) {
        User user = SecurityUtil.getCurrentUser();
        if(!user.getEmail().equals(param.getEmail())){
            checkUniqueEmail(param.getEmail());
        }
        user.setEmail(param.getEmail());
        return userRepository.save((USER) user);
    }
}
