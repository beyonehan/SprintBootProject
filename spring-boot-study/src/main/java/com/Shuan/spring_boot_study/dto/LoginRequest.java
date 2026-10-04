package com.Shuan.spring_boot_study.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank String password
) {
}
