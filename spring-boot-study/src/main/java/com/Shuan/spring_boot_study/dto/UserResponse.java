package com.Shuan.spring_boot_study.dto;

import com.Shuan.spring_boot_study.model.User;
import com.Shuan.spring_boot_study.model.UserStatus;

import java.time.Instant;

public record UserResponse(
        Long id ,
        String name,
        String email,
        UserStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static  UserResponse from(User user) {
        return  new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
