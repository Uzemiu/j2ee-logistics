package com.example.logistics.model.dto;

import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.Vehicle;
import com.example.logistics.model.enums.OrderStatus;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Data
public class OrderDTO {

    private Long id;

    private UserDTO sender;

    private String itemName;

    private Double itemWeight;

    private Double itemVolume;

    private BigDecimal price;

    private String sendAddress;

    private String senderName;

    private String receiveAddress;

    private String receiverPhone;

    private String receiverName;

    private OrderStatus status;

    private String comment;

//    private Vehicle transportVehicle;
}
