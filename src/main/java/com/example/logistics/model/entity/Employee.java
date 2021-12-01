package com.example.logistics.model.entity;

import com.example.logistics.model.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_employee")
public class Employee extends User{

    @Column(name = "real_name", columnDefinition = "varchar(16) default '真名'", nullable = false)
    private String realName;

    @Column(name = "role", columnDefinition = "int(11) default 0", nullable = false)
    Role role;

}
