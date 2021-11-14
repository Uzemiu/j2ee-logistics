package com.example.logistics.model.param;

import com.example.logistics.model.enums.Role;
import lombok.Data;

@Data
public class AddEmployeeParam extends RegisterParam{

    private String realName;

    private Role role;
}
