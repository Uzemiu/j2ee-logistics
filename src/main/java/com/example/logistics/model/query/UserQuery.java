package com.example.logistics.model.query;

import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.query.BaseQuery;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserQuery<USER extends User> extends BaseQuery<USER> {

    private String email;

    private String phoneNumber;

    private Integer gender;

    @Override
    public Specification<USER> toSpecification() {
        Specification<USER> specification = super.toSpecification();
        return specification.and((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.hasText(email)){
                criteriaBuilder.like(root.get("email"),"%" + email + "%");
            }
            if(StringUtils.hasText(phoneNumber)){
                criteriaBuilder.like(root.get("phoneNumber"),"%" + phoneNumber + "%");
            }
            if(gender != null){
                predicates.add(criteriaBuilder.equal(root.get("gender"), gender));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
