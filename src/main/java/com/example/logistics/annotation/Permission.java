package com.example.logistics.annotation;

import com.example.logistics.model.enums.Role;
import com.example.logistics.model.entity.User;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    boolean allowClient() default false;

    boolean allowEmployee() default false;

    Role[] allowRoles() default {};
}
