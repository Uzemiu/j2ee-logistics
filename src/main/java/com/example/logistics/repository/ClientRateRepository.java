package com.example.logistics.repository;

import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.ClientRate;
import com.example.logistics.model.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRateRepository extends BaseRepository<ClientRate, Long>{

    long countByOrderAndClient(Order order, Client client);

    Optional<ClientRate> findByOrder(Order order);

    Optional<ClientRate> findByOrderAndClient(Order order, Client client);
}
