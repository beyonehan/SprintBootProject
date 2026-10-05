package com.Shuan.spring_boot_study.service;

import com.Shuan.spring_boot_study.dto.AuthResponse;
import com.Shuan.spring_boot_study.dto.LoginRequest;
import com.Shuan.spring_boot_study.dto.UserResponse;
import com.Shuan.spring_boot_study.exception.DuplicateEmailException;
import com.Shuan.spring_boot_study.exception.InvalidCredentialsException;
import com.Shuan.spring_boot_study.model.User;
import com.Shuan.spring_boot_study.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.Shuan.spring_boot_study.dto.RegisterRequest;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private  final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    @Transactional
    public UserResponse register(RegisterRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new DuplicateEmailException(normalizedEmail);
        }
        String passwordHash = passwordEncoder.encode(request.password());
        User user = new User(
                request.name().trim(),
                normalizedEmail,
                passwordHash
        );
        return UserResponse.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();

        User user = userRepository
                .findByEmailIgnoreCase(normalizedEmail)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(
                request.password(),
                user.getPasswordHash()
        )) {
            throw  new InvalidCredentialsException();
        }

        String accessToken = jwtService.issueToken(user);
        return  new AuthResponse(
                accessToken,
                "Bearer",
                jwtService.getExpirationSeconds()
        );
    }
}
