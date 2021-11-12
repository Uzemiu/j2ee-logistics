package com.example.logistics.model.entity;

import com.example.logistics.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "tbl_order")
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    /**
     * 发件方
     */
    @ManyToOne
    @JoinColumn(name = "sender_client_id")
    private Client sender;

    /**
     *
     */
    @Column(name = "item_name", columnDefinition = "varchar(127)", nullable = false)
    private String itemName;

    @Column(name = "item_weight", columnDefinition = "decimal(19,2)", nullable = false)
    private Double itemWeight;

    @Column(name = "item_volume", columnDefinition = "decimal(19,2) default 0.0")
    private Double itemVolume;

    /**
     * 订单价格
     */
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "send_address", columnDefinition = "varchar(1023)", nullable = false)
    private String sendAddress;

    @Column(name = "sender_name", columnDefinition = "varchar(16)", nullable = false)
    private String senderName;

    @Column(name = "receive_address", columnDefinition = "varchar(1023)", nullable = false)
    private String receiveAddress;

    @Column(name = "receiver_phone", columnDefinition = "varchar(31)", nullable = false)
    private String receiverPhone;

    @Column(name = "receiver_name", columnDefinition = "varchar(31)", nullable = false)
    private String receiverName;

    @Column(name = "order_status", nullable = false)
    private OrderStatus status;

    @Column(name = "comment", columnDefinition = "varchar(1023) default ''")
    private String comment;

    /**
     * 分配车辆
     */
    @OneToOne
    @JoinColumn(name = "transport_vehicle_id")
    private Vehicle transportVehicle;

}
