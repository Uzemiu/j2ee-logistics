package com.example.logistics.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 车辆信息管理
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "tbl_vehicle")
public class Vehicle extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long id;

    @Column(name = "vehicle_number", columnDefinition = "varchar(31)", nullable = false)
    private String vehicleNumber;

    @Column(name = "status")
    private Integer status;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private Employee driver;
}
