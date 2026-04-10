package com.ume.studentsystem.auth.jwt;

import com.ume.studentsystem.config.JwtConfig;
import com.ume.studentsystem.model.AppUser;
import com.ume.studentsystem.model.Permission;
import com.ume.studentsystem.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;

    public Jwt generateAccessToken(AppUser user){
        return generateToken(user,jwtConfig.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(AppUser user){
        return generateToken(user,jwtConfig.getRefreshTokenExpiration());
    }

    public Jwt parseToken(String token){
        try {
            var claim = getClaims(token);
            return new Jwt(claim,jwtConfig.secretKey());
        }catch (JwtException e){
            return null;
        }
    }

    private Jwt generateToken(AppUser user , long tokenExpiration){

        List<String> permissions = user.getRoles()
                .stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getName)
                .distinct()
                .toList();
        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        var claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("email",user.getEmail())
                .add("name", user.getUsername())
                .add("role", roles)
                .add("permissions" , permissions)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();
        return new Jwt(claims,jwtConfig.secretKey());
    }

    public Set<String> extractPermissions(String token) {
        var claims = getClaims(token);
        List<String> list = (List<String>) claims.get("permissions");
        return new HashSet<>(list);
    }

    public String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
