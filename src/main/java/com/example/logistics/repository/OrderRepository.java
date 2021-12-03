package com.example.logistics.repository;

import com.example.logistics.model.entity.Order;
import com.example.logistics.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Transactional
    @Modifying
    @Query("update Order o set o.payed = :payed where o.id = :id")
    void updatePayedStatus(@Param("id") Long id, @Param("payed") boolean payed);

    Optional<Order> findByTransportVehicle(Vehicle transportVehicle);
}
