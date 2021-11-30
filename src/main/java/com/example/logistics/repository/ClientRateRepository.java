package com.example.logistics.repository;

import com.example.logistics.model.entity.ClientRate;
import com.example.logistics.model.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRateRepository extends BaseRepository<ClientRate, Long>{

    long countByOrder(Order order);

    Optional<ClientRate> findByOrder(Order order);
}
