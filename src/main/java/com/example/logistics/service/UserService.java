package com.example.logistics.service;

import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.User;
import com.example.logistics.service.base.CrudService;

public interface UserService<USER extends User> extends CrudService<USER, Long> {

    UserDTO toDto(USER user);
}
