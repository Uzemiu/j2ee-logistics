package com.example.logistics.model.query;

import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.Order;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.enums.OrderStatus;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderQuery extends BaseQuery<Order>{

    private Client sender;

    private OrderStatus status;

    @Override
    public Specification<Order> toSpecification() {
        Specification<Order> specification = super.toSpecification();
        return specification.and((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(sender != null){
                predicates.add(criteriaBuilder.equal(root.get("sender"), sender));
            }
//            if(StringUtils.hasText(name)){
//                Predicate nLike = criteriaBuilder.like(root.get("name"),"%" + name + "%");
//                Predicate iLike = criteriaBuilder.like(root.get("introduction"),"%" + name + "%");
//                predicates.add(criteriaBuilder.or(nLike,iLike));
//            }
//            if(StringUtils.hasText(link)){
//                predicates.add(criteriaBuilder.like(root.get("link"),"%" + link + "%"));
//            }
            if(status != null){
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
