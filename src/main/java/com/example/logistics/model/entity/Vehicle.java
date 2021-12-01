package com.example.logistics.model.entity;

import com.example.logistics.model.enums.VehicleStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @NotNull(message = "车牌号不能为空")
    @Length(max = 31, message = "车牌号长度补鞥拆过31个字符")
    @Column(name = "vehicle_number", columnDefinition = "varchar(31)", nullable = false)
    private String vehicleNumber;

    @Column(name = "status", columnDefinition = "int(11) default 0")
    private VehicleStatus status;

    @ApiModelProperty(notes = "只需要传ID")
    @OneToOne
    @JoinColumn(name = "driver_id")
    private Employee driver;
}
