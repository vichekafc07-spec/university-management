package com.ume.studentsystem.config;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@Data
public class JwtConfig {
    @Value("${spring.jwt.secretKey}")
    private String secret;
    @Value("${spring.jwt.accessExpiration}")
    private int accessTokenExpiration;
    @Value("${spring.jwt.refreshExpiration}")
    private int refreshTokenExpiration;

    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
