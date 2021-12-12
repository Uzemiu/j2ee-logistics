package com.example.logistics.model.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum OrderStatus {

    /**
     * 默认状态
     */
    ORDER_CREATED("订单已创建"),
    /**
     * 未发货
     */
    NOT_SENT("等待发货"),
    /**
     * 待运输(已揽件)
     */
    WAITING_FOR_TRANSPORTATION("货物等待运输"),
    /**
     * 运输中
     */
    ON_THE_WAY("货物在运输途中"),
    /**
     * 已送达
     */
    ALREADY_ARRIVED("货物已送达"),
    /**
     * 确认收货
     */
    RECEIPT_CONFIRMED("确认收货"),
    /**
     * 已取消
     */
    CANCELLED("订单已被取消"),
    /**
     * 丢失
     */
    LOST("货物丢失");

    private final String description;

    public boolean lessThan(OrderStatus status){
        return ordinal() < status.ordinal();
    }

    public boolean greaterThan(OrderStatus status){
        return ordinal() > status.ordinal();
    }

    public boolean lessThanOrEqual(OrderStatus status){
        return ordinal() <= status.ordinal();
    }

    public boolean greaterThanOrEqual(OrderStatus status){
        return ordinal() >= status.ordinal();
    }

    /**
     *
     * @param s1 included
     * @param s2 included
     * @return
     */
    public boolean between(OrderStatus s1, OrderStatus s2){
        return ordinal() >= s1.ordinal() && ordinal() <= s2.ordinal();
    }
}
