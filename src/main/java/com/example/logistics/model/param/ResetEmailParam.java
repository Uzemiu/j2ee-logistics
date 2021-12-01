package com.example.logistics.model.param;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class ResetEmailParam extends CaptchaParam{

    @Email(message = "邮箱格式不合法")
    @NotNull(message = "邮箱不能为空")
    String email;
}
