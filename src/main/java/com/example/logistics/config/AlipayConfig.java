package com.example.logistics.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Configuration
@ConfigurationProperties("alipay")
public class AlipayConfig {

    private String appId = "";

    // 商户私钥，您的PKCS8格式RSA2私钥  刚刚生成的私钥直接复制填写
    private String privateKey = "";

    // 支付宝公钥,对应APPID下的支付宝公钥。 按照我文章图上的信息填写支付宝公钥，别填成商户公钥
    private String publicKey = "";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，其实就是你的一个支付完成后返回的页面URL
    private String notifyUrl = "http://localhost/api/alipay/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，其实就是你的一个支付完成后返回的页面URL
    private String returnUrl = "http://localhost/api/alipay/return";

    // 签名方式
    private String signType = "RSA2";

    // 字符编码格式
    private String charset = "utf-8";

    // 支付宝网关
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
}
