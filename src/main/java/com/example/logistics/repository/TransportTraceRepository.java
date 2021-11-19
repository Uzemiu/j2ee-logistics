package com.example.logistics.repository;

import com.example.logistics.model.entity.Order;
import com.example.logistics.model.entity.TransportTrace;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportTraceRepository extends BaseRepository<TransportTrace, Long>{

    List<TransportTrace> findByOrder(Order order);
}
