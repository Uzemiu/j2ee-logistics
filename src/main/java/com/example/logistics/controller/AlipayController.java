package com.example.logistics.controller;

import com.alipay.api.AlipayApiException;
import com.example.logistics.annotation.AnonymousAccess;
import com.example.logistics.config.AlipayConfig;
import com.example.logistics.model.enums.AliPayStatusEnum;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.service.AlipayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@ApiIgnore
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/alipay")
public class AlipayController {

    private final AlipayService alipayService;

    private final AlipayConfig alipayConfig;

    @AnonymousAccess
    @GetMapping("/return")
    public BaseResponse<?> returnPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=" + alipayConfig.getCharset());
        //内容验签，防止黑客篡改参数
        if (alipayService.rsaCheck(request)) {
            //商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            System.out.println("商户订单号" + outTradeNo + "  " + "第三方交易号" + tradeNo);
            // 根据业务需要返回数据，这里统一返回OK
            response.sendRedirect("localhost/api/");
            return BaseResponse.ok("ok","商户订单号" + outTradeNo + "  " + "第三方交易号" + tradeNo);
        } else {
            // 根据业务需要返回数据
            return BaseResponse.error();
        }
    }

    @AnonymousAccess
    @RequestMapping("notify")
    public BaseResponse<?> notify(HttpServletRequest request, HttpServletResponse response){
        Map<String, String[]> parameterMap = request.getParameterMap();
        //内容验签，防止黑客篡改参数
        Assert.isTrue(alipayService.rsaCheck(request), "内容校验失败");
        //交易状态
        String tradeStatus = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        // 商户订单号
        String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        //支付宝交易号
        String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        //付款金额
        String totalAmount = new String(request.getParameter("total_amount").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        //验证
        if (tradeStatus.equals(AliPayStatusEnum.SUCCESS.getValue()) || tradeStatus.equals(AliPayStatusEnum.FINISHED.getValue())) {
            // 验证通过后应该根据业务需要处理订单
        }
        return BaseResponse.ok();
    }
}
