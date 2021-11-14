package com.example.logistics.model.enums;

public enum OrderStatus {

    /**
     * 未付款
     */
    NOT_PAID,
    /**
     * 未发货
     */
    NOT_SENT,
    /**
     * 待运输(已揽件)
     */
    WAITING_FOR_TRANSPORTATION,
    /**
     * 运输中
     */
    ON_THE_WAY,
    /**
     * 已送达
     */
    ALREADY_ARRIVED,
    /**
     * 确认收货
     */
    RECEIPT_CONFIRMED,
    /**
     * 已取消
     */
    CANCELLED,
    /**
     * 丢失
     */
    LOST;

    public boolean lessThan(OrderStatus status){
        return ordinal() < status.ordinal();
    }

    public boolean greaterThan(OrderStatus status){
        return ordinal() > status.ordinal();
    }

    public boolean lessThanOrEqual(OrderStatus status){
        return ordinal() <= status.ordinal();
    }

    public boolean greaterThanOeEqual(OrderStatus status){
        return ordinal() >= status.ordinal();
    }
}
