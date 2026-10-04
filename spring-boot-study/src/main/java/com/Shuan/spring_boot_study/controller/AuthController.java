package com.Shuan.spring_boot_study.controller;

import com.Shuan.spring_boot_study.dto.LoginRequest;
import com.Shuan.spring_boot_study.dto.RegisterRequest;
import com.Shuan.spring_boot_study.dto.UserResponse;
import com.Shuan.spring_boot_study.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

public class AuthController {

    private  final AuthService authService;
    public  AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }
//    @PostMapping('/login')
//    public UserResponse login(@Valid @RequestBody LoginRequest loginRequest) {
//        return  authService.
//    }
}
