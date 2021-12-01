package com.example.logistics.model.query;

import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.enums.Role;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeQuery extends UserQuery<Employee> {

    private Role role;

    @Override
    public Specification<Employee> toSpecification() {
        Specification<Employee> specification = super.toSpecification();
        return specification.and((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(role != null){
                predicates.add(criteriaBuilder.equal(root.get("role"), role));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
