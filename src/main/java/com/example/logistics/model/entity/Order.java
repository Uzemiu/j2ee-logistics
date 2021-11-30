package com.example.logistics.model.entity;

import com.example.logistics.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
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
    @JoinColumn(name = "sender_client_id", nullable = false)
    private Client sender;

    /**
     *
     */
    @NotNull(message = "物品名称不能为空")
    @Length(max = 127, message = "物品名称长度需小于127个字符")
    @Column(name = "item_name", columnDefinition = "varchar(127)", nullable = false)
    private String itemName;

    @NotNull(message = "物品重量不能为空")
    @Positive(message = "物品重量只能是正数")
    @Column(name = "item_weight", columnDefinition = "decimal(19,2)", nullable = false)
    private Double itemWeight;

    @Positive(message = "物品体积只能是正数")
    @Column(name = "item_volume", columnDefinition = "decimal(19,2) default 0.0")
    private Double itemVolume;

    @NotNull(message = "物品数量不能为空")
    @Positive(message = "物品数量只能是正数")
    @Column(name = "item_count", columnDefinition = "int(11) default 1", nullable = false)
    private Integer itemCount;

    /**
     * 订单价格
     */
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @NotBlank(message = "发货人地址不能为空")
    @Length(max = 1023, message = "发货地址需小于1023个字符之间")
    @Column(name = "send_address", columnDefinition = "varchar(1023)", nullable = false)
    private String sendAddress;

    @NotBlank(message = "发货人姓名不能为空")
    @Length(max = 16, message = "发货人姓名需小于16个字符之间")
    @Column(name = "sender_name", columnDefinition = "varchar(16)", nullable = false)
    private String senderName;

    @NotBlank(message = "收货人地址不能为空")
    @Length(max = 1023, message = "收货地址需小于1023个字符之间")
    @Column(name = "receive_address", columnDefinition = "varchar(1023)", nullable = false)
    private String receiveAddress;

    @NotBlank(message = "收货人手机号不能为空")
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$", message = "收货人手机号不合法")
    @Column(name = "receiver_phone", columnDefinition = "varchar(31)", nullable = false)
    private String receiverPhone;

    @NotBlank(message = "收货人姓名不能为空")
    @Length(max = 16, message = "收货人姓名需小于16个字符之间")
    @Column(name = "receiver_name", columnDefinition = "varchar(31)", nullable = false)
    private String receiverName;

    @Column(name = "order_status", columnDefinition = "int(11) default 0", nullable = false)
    private OrderStatus status;

    @Column(name = "is_payed", columnDefinition = "bit(1) default false", nullable = false)
    private Boolean payed;

    @Length(max = 1023, message = "备注不能超过1023个字符")
    @Column(name = "comment", columnDefinition = "varchar(1023) default ''")
    private String comment;

    /**
     * 分配车辆
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_vehicle_id", unique = true /*一辆车最多被分配到一个订单*/)
    private Vehicle transportVehicle;

}
