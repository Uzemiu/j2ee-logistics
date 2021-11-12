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
     * 丢失
     */
    LOST,
}
