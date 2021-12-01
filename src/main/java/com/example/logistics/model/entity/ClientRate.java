package com.example.logistics.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "tbl_client_rate")
public class ClientRate extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_rate_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ApiModelProperty(notes = "只需传入ID")
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "item_score", columnDefinition = "int(11) default 5")
    private Integer itemScore;

    @Column(name = "service_score", columnDefinition = "int(11) default 5")
    private Integer serviceScore;

    @Column(name = "logistics_score", columnDefinition = "int(11) default 5")
    private Integer logisticsScore;

    @Column(name = "advice", columnDefinition = "varchar(1023) default ''")
    private String advice;

    @Column(name = "logistic_issue", columnDefinition = "varchar(1023) default ''")
    private String issue;


}
