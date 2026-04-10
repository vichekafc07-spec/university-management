package com.ume.studentsystem.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class Jwt {
    private final Claims claims;
    private final SecretKey secretKey;

    public boolean isExpiration(){
        return claims.getExpiration().before(new Date());
    }

    public Date getExpiration(){
        return claims.getExpiration();
    }

    public Long getUserId(){
        return Long.valueOf(claims.getSubject());
    }
    public String getUserName(){
        return claims.getSubject();
    }
    public List<String> getRole(){
        return claims.get("role", List.class);
    }
    public List<String> getPermission(){
        return claims.get("permissions", List.class);
    }

    public String generate(){
        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }

}