package com.ume.studentsystem.config;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtConfig {
    private String secret;
    private int accessTokenExpiration;
    private int refreshTokenExpiration;

    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
