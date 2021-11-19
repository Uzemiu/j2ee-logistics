package com.example.logistics.model.query;

import com.example.logistics.model.entity.Order;
import com.example.logistics.model.entity.Vehicle;
import com.example.logistics.model.enums.VehicleStatus;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
public class VehicleQuery extends BaseQuery<Vehicle>{

    private VehicleStatus status;

    @Override
    public Specification<Vehicle> toSpecification() {
        Specification<Vehicle> specification = super.toSpecification();
        return specification.and((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(status != null){
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
