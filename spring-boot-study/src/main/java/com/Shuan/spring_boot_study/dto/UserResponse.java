package com.Shuan.spring_boot_study.dto;

import com.Shuan.spring_boot_study.model.User;

public record UserResponse(Long id , String name) {
    public static  UserResponse from(User user) {
        return  new UserResponse(user.getId(), user.getName());
    }
}
