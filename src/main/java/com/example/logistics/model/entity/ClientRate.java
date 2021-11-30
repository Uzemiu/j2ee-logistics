package com.example.logistics.model.entity;

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

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Range(min = 0, max = 5, message = "分数只能在0到5之间")
    @NotNull(message = "评分不能为空")
    @Column(name = "score", columnDefinition = "int(11) default 5")
    private Integer score;

    @Length(max = 1023, message = "评价信息不能超过1023个字符")
    @Column(name = "comment", columnDefinition = "varchar(1023) default ''")
    private String comment;

}
