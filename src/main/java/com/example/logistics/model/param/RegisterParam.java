package com.example.logistics.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegisterParam {

    @Pattern(regexp = "^[0-9A-Za-z]{6,31}$",
            message = "用户名长度须在6~31个字符之间且只能由字母和数字组成")
    private String username;

    @Pattern(regexp = "^[0-9A-Za-z@#$%^&*()_+!]{6,31}$",
            message = "密码长度须在6~31个字符之间且只能包含大小写字母，数字与@#$%^&*()_+!")
    private String password;

    @Email(message = "邮件格式不合法")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$", message = "手机号不合法")
    @NotBlank(message = "手机号不能为空")
    private String phoneNumber;

    /**
     * 如果需要短信/邮箱验证
     * 需附带验证码UUID以及验证码本身
     */
    private String captchaUuid;
    private String captcha;
}
