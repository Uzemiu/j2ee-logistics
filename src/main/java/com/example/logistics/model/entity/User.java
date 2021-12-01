package com.example.logistics.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(of = "id", callSuper = false)
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", columnDefinition = "varchar(63)", unique = true, nullable = false)
    private String username;

    @Column(name = "password", columnDefinition = "varchar(127)", nullable = false)
    private String password;

    @Column(name = "email", columnDefinition = "varchar(255)", unique = true, nullable = false)
    private String email;

    @Column(name = "phone_number", columnDefinition = "varchar(31)", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "gender", columnDefinition = "int(11) default 0")
    private Integer gender;

    @Override
    protected void prePersist() {
        super.prePersist();
        if(gender == null){
            gender = 0;
        }
    }
}
