package com.example.logistics.model.dto;

import com.example.logistics.model.enums.Role;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String username;

    private String realName;

    private Role role;
}
