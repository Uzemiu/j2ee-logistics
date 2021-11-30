package com.example.logistics.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ResetPasswordParam {

    @NotNull(message = "原密码不能为空")
    private String oldPassword;

    @NotNull(message = "新密码不能为空")
    @Pattern(regexp = "^[0-9A-Za-z@#$%^&*()_+!]{6,31}$",
            message = "密码长度须在6~31个字符之间且只能包含大小写字母，数字与@#$%^&*()_+!")
    private String newPassword;
}
