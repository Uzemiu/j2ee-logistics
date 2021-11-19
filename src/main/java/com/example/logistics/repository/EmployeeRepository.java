package com.example.logistics.repository;

import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.enums.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends UserRepository<Employee>{

    long countByRole(Role role);
}
