package com.example.logistics.controller;

import com.example.logistics.annotation.AnonymousAccess;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.model.support.Captcha;
import com.example.logistics.service.CaptchaService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/captcha")
@RequiredArgsConstructor
public class CaptchaController {

    private final CaptchaService captchaService;

    @ApiOperation("获取验证码")
    @AnonymousAccess
    @GetMapping
    public BaseResponse<Captcha> generateCaptcha(){
        return BaseResponse.ok("ok",captchaService.generateImageCaptcha());
    }
}
