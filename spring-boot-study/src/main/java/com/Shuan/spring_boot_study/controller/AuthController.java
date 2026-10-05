package com.Shuan.spring_boot_study.controller;

import com.Shuan.spring_boot_study.dto.LoginRequest;
import com.Shuan.spring_boot_study.dto.RegisterRequest;
import com.Shuan.spring_boot_study.dto.UserResponse;
import com.Shuan.spring_boot_study.dto.AuthResponse;
import com.Shuan.spring_boot_study.service.AuthService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private  final AuthService authService;
    public  AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "注册用户")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "注册成功"),
    @ApiResponse(responseCode = "400", description = "参数校验失败"),
    @ApiResponse(responseCode = "409", description = "邮箱已存在")
})

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return  authService.login(loginRequest);
    }



}
