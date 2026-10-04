package com.Shuan.spring_boot_study.dto;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long   expiresIn
) {
}
