package com.Shuan.spring_boot_study.config;

import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtConfig {
    @Bean
    public SecretKey jwtSecretKey(
            @Value("${security.jwt.secret}") String encodedSecret
    ) {
        byte[] keyBytes = Base64.getDecoder().decode(encodedSecret);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException(
                    "JWT_SECRET 解码至少需要32字节"
            );
        }
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    @Bean
    public JwtEncoder jwtEncoder(SecretKey secretKey) {
        return NimbusJwtEncoder.withSecretKey(secretKey)
                .algorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean
    public  JwtDecoder jwtDecoder(SecretKey secretKey) {
        return  NimbusJwtDecoder.withSecretKey( secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }
}
