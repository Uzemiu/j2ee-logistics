package com.example.logistics.repository;

import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends UserRepository<Client>{

}
