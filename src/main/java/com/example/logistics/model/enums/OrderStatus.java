package com.example.logistics.model.enums;

public enum OrderStatus {

    /**
     * 默认状态
     */
    ORDER_CREATED,
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
