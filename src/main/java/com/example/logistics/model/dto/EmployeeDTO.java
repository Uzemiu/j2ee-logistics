package com.example.logistics.model.dto;

import com.example.logistics.model.enums.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class EmployeeDTO extends UserDTO{

    @Length(max = 15, message = "真名不能超过15个字符")
    private String realName;

    private Role role;

    private String email;

    private String phoneNumber;
}
