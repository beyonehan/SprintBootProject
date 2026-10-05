package com.Shuan.spring_boot_study.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.Shuan.spring_boot_study.model.User;

import java.time.Instant;

@Service
public class JwtService {
    private  final JwtEncoder jwtEncoder;
    private  final long expirationSeconds;

    public  JwtService(
            JwtEncoder jwtEncoder,
            @Value("${security.jwt.expiration-seconds}") long expirationSeconds
    ) {
        this.jwtEncoder = jwtEncoder;
        this.expirationSeconds = expirationSeconds;
    }

    public String issueToken(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("sprint-boot-study")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationSeconds))
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .build();
        return  jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }
    public  long getExpirationSeconds(){
        return  expirationSeconds;
    }

}
