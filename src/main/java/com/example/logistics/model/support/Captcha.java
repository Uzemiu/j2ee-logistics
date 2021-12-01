package com.example.logistics.model.support;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Captcha {

    /**
     * 验证码答案
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String code;

    /**
     * 显示验证码的实体形式
     */
    private Object entity;

    /**
     * 用于从缓存获取验证码
     */
    private String uuid;
}
