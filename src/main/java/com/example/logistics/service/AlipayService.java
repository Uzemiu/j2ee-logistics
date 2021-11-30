package com.example.logistics.service;

import com.alipay.api.AlipayApiException;
import com.example.logistics.model.entity.Order;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

public interface AlipayService {

    String payAsPc(Order order) throws AlipayApiException;

    boolean rsaCheck(HttpServletRequest request);
}
