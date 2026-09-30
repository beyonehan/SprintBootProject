package com.Shuan.spring_boot_study.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "用户名不能为空")
        @Size(min = 2,max = 50, message = "用户名长度必须在2到50个字符之间")
        String name
) {

}
