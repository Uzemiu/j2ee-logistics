package com.example.logistics.repository;

import com.example.logistics.model.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<USER extends User> extends BaseRepository<USER, Long>, JpaSpecificationExecutor<USER> {

    Optional<USER> findByUsername(String username);

    long countByEmail(String email);

    long countByUsername(String username);

    long countByPhoneNumber(String phoneNumber);
}
