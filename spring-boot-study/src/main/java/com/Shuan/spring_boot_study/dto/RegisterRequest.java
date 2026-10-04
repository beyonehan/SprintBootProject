package com.Shuan.spring_boot_study.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 50, message = "用户名长度必须在2到50个字符之间")
    String name,

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    String email,

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 72, message = "密码长度必须在8到72个字符之间")
    String password
) {
}
