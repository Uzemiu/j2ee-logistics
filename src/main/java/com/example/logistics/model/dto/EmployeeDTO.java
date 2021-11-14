package com.example.logistics.model.dto;

import lombok.Data;

@Data
public class EmployeeDTO extends UserDTO{

    private String email;

    private String phoneNumber;
}
