package com.example.logistics.model.dto;

import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.Vehicle;
import com.example.logistics.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDTO {

    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDTO sender;

    private String itemName;

    private Integer itemCount;

    private Double itemWeight;

    private Double itemVolume;

    private BigDecimal price;

    private String sendAddress;

    private String senderName;

    private String receiveAddress;

    private String receiverPhone;

    private String receiverName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OrderStatus status;

    private String comment;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean payed;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
