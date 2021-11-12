package com.example.logistics.repository;

import com.example.logistics.model.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long>{
}
