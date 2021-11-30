package com.example.logistics.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.example.logistics.config.AlipayConfig;
import com.example.logistics.exception.BadRequestException;
import com.example.logistics.model.entity.Order;
import com.example.logistics.service.AlipayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service("alipayService")
@RequiredArgsConstructor
public class AlipayServiceImpl implements AlipayService {

    private final AlipayConfig config;

    @Override
    public String payAsPc(Order order) throws AlipayApiException {

        AlipayClient alipayClient = new DefaultAlipayClient(config.getGatewayUrl(), config.getAppId(), config.getPrivateKey(), "JSON", config.getCharset(), config.getPublicKey(), config.getSignType());

        // 创建API对应的request(电脑网页版)
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();

        // 订单完成后返回的页面和异步通知地址
        request.setReturnUrl(config.getReturnUrl());
        request.setNotifyUrl(config.getNotifyUrl());
        // 填充订单参数
        String outTraceNo = order.getCreateTime().getTime()+"-"+order.getId().toString();
        request.setBizContent("{" +
                "    \"out_trade_no\":\""+outTraceNo+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":"+order.getPrice()+"," +
                "    \"subject\":\""+order.getItemName()+"运费\"," +
                "    \"body\":\"商品描述\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\""+"2088621955931566"+"\"" +
                "    }"+
                "  }");//填充业务参数
        // 调用SDK生成表单, 通过GET方式，口可以获取url
        return alipayClient.pageExecute(request, "GET").getBody();
    }

    @Override
    public boolean rsaCheck(HttpServletRequest request) {
        Map<String,String> params = new HashMap<>(1);
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String o : requestParams.keySet()) {
            String[] values = requestParams.get(o);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(o, valueStr);
        }

        try {
            return AlipaySignature.rsaCheckV1(params,
                    config.getPublicKey(),
                    config.getCharset(),
                    config.getSignType());
        } catch (AlipayApiException e) {
            return false;
        }
    }
}
