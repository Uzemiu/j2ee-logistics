package com.example.logistics.config;

import cn.hutool.crypto.digest.BCrypt;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.enums.Role;
import com.example.logistics.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitializationConfig {

    private final EmployeeRepository employeeRepository;

    @EventListener
    public void setDefaultAdmin(ContextRefreshedEvent event){
        if(employeeRepository.countByRole(Role.ADMIN) == 0){
            Employee admin = new Employee();
            admin.setRole(Role.ADMIN);
            admin.setRealName("管理员");
            admin.setEmail("admin@admin.com");
            admin.setPhoneNumber("15811111111");
            admin.setUsername("admin");
            admin.setPassword(BCrypt.hashpw("admin", BCrypt.gensalt()));
            employeeRepository.save(admin);
        }
    }
}
