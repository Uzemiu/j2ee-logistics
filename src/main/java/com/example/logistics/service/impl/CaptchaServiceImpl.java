package com.example.logistics.service.impl;

import cn.hutool.core.lang.Assert;
import com.example.logistics.model.param.CaptchaParam;
import com.example.logistics.model.support.Captcha;
import com.example.logistics.service.CaptchaService;
import com.wf.captcha.ArithmeticCaptcha;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service("captchaService")
public class CaptchaServiceImpl implements CaptchaService {

    private final ConcurrentHashMap<String, ExpireObject<String>> captchaCache = new ConcurrentHashMap<>();

    @Override
    public Captcha generateImageCaptcha() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111,36,2);
        String verCode = captcha.text().toLowerCase();
        if(verCode.contains(".")){
            verCode = verCode.split("\\.")[0];
        }
        String uuid = UUID.randomUUID().toString();
        captchaCache.put(uuid, new ExpireObject<>(verCode, 10*60*1000)); // expire in 10 minutes
        return new Captcha(verCode, captcha.toBase64(), uuid);
    }

    @Override
    public void assertCaptcha(CaptchaParam captcha) {
        Assert.isTrue(checkCaptcha(captcha), "验证码错误或已过期");
    }

    @Override
    public boolean checkCaptcha(CaptchaParam captcha) {
        return captcha != null && checkCaptcha(captcha.getCaptchaUuid(), captcha.getCaptcha());
    }

    @Override
    public boolean checkCaptcha(String uuid, String code) {
        ExpireObject<String> captcha = captchaCache.remove(uuid);
        return captcha != null && !captcha.expire() && Objects.equals(captcha.data, code);
    }

    static class ExpireObject<T>{

        T data;

        long expire;

        public ExpireObject(T data, long expire){
            this.data = data;
            this.expire = System.currentTimeMillis() + expire;
        }

        boolean expire(){
            return expire < System.currentTimeMillis();
        }
    }
}
