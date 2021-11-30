package com.example.logistics.service;

import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.param.LoginParam;
import com.example.logistics.model.param.ResetPasswordParam;
import com.example.logistics.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService<USER extends User> extends CrudService<USER, Long> {

    /**
     *
     * @param param
     * @return 登陆成功返回登录用户信息
     * @throws IllegalArgumentException 登录失败
     */
    USER login(LoginParam param);

    UserDTO toDto(User user);

    Page<USER> query(Pageable pageable);

    void checkUniqueUsername(String username);

    void checkUniqueEmail(String email);

    void checkUniquePhone(String phone);

    void checkUniqueColumn(String username, String email, String phoneNumber);

    USER resetPassword(ResetPasswordParam param);
}
