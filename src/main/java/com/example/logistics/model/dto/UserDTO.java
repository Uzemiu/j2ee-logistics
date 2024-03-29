package com.example.logistics.model.dto;

import com.example.logistics.model.enums.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserDTO {

    private Long id;

    @Length(max = 63, message = "用户名不能超过63个字符")
    private String username;

    private Integer gender;

    private String email;

    private String phoneNumber;
}
