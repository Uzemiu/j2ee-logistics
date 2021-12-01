package com.example.logistics.service;

import com.example.logistics.model.dto.OrderDTO;
import com.example.logistics.model.dto.OrderDetailDTO;
import com.example.logistics.model.entity.Order;
import com.example.logistics.model.query.OrderQuery;
import com.example.logistics.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService extends CrudService<Order, Long> {

    Page<Order> queryBy(OrderQuery query, Pageable pageable);

    /**
     * 用户或管理员取消订单
     * @param id
     * @return
     */
    boolean cancelOrder(Long id);

    /**
     * 用户确认订单
     * @param id
     */
    void confirmOrder(Long id);

    /**
     * 转换订单基本信息（不包括车辆信息）
     * @param order
     * @return
     */
    OrderDTO toDto(Order order);

    /**
     * 转换订单详细信息（包括车辆信息）
     * @param order
     * @return
     */
    OrderDetailDTO toDetailDto(Order order);

    /**
     * 支付订单<br>
     * 本次项目直接更新订单支付状态
     * @param orderId
     * @return 支付地址URL
     */
    String payOrder(Long orderId);

    Order assignVehicle(Long orderId, Long vehicleId);

}
