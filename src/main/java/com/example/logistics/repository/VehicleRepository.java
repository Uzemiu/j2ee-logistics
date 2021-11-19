package com.example.logistics.repository;

import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface VehicleRepository extends BaseRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {

    long countByDriver(Employee driver);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select v from Vehicle v where v.id=:id")
    Optional<Vehicle> findByIdPessimistic(Long id);

}
