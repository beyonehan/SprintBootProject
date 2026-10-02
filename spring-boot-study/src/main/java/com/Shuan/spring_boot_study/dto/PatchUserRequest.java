package com.Shuan.spring_boot_study.dto;

import com.Shuan.spring_boot_study.model.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PatchUserRequest(
        @Pattern(regexp = ".*\\S.*", message = "用户名不能全是空白字符")
        @Size(min = 2, max = 50, message = "用户名长度必须在2到50个字符之间")
        String name,

        @Email(message = "邮箱格式不正确")
        @Size(max = 255, message = "邮箱长度不能超过255个字符")
        String email,

        UserStatus status
) {
}
