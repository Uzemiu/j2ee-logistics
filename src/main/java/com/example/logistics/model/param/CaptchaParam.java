package com.example.logistics.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CaptchaParam {

//    @NotBlank(message = "验证码不能为空")
    private String captcha;

//    @NotBlank
    private String captchaUuid;
}
