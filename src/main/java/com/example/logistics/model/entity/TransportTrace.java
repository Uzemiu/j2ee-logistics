package com.example.logistics.model.entity;

import com.example.logistics.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "tbl_transport_trace")
public class TransportTrace extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_trace_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "information", columnDefinition = "varchar(255) default ''")
    private String information;

    @Column(name = "trace_time")
    private Date traceTime;

    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Override
    protected void prePersist() {
        super.prePersist();
        if(information == null){
            information = "";
        }
        if(traceTime == null){
            traceTime = new Date();
        }
    }
}
