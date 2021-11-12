package com.example.logistics.service.base;

import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.entity.User;
import com.example.logistics.repository.BaseRepository;
import com.example.logistics.repository.UserRepository;
import com.example.logistics.service.UserService;
import org.springframework.beans.BeanUtils;

public abstract class AbstractUserService<USER extends User> extends AbstractCrudService<USER, Long> implements UserService<USER> {

    protected AbstractUserService(UserRepository<USER> repository) {
        super(repository);
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
}
