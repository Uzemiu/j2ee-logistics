package com.example.logistics.service;

import com.example.logistics.model.param.CaptchaParam;
import com.example.logistics.model.support.Captcha;

public interface CaptchaService {

    Captcha generateImageCaptcha();

    void assertCaptcha(CaptchaParam captcha);

    boolean checkCaptcha(CaptchaParam captcha);

    boolean checkCaptcha(String uuid, String code);
}
