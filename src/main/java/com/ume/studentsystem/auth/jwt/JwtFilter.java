package com.ume.studentsystem.auth.jwt;

import com.ume.studentsystem.repository.BlackListedTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final BlackListedTokenRepository blackListedTokenRepository; // <-- ADDED

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = authHeader.replace("Bearer ", "");

        if (blackListedTokenRepository.existsByToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token has been logged out\"}");
            return;
        }

        var jwt = jwtService.parseToken(token);
        if (jwt == null || jwt.isExpiration()) {
            filterChain.doFilter(request, response);
            return;
        }

        var roles = jwt.getRole();
        var permission = jwt.getPermission();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        roles.forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role)));

        permission.forEach(p ->
                authorities.add(new SimpleGrantedAuthority(p)));

        var authentication = new UsernamePasswordAuthenticationToken(
                jwt.getUserId(),
                null,
                authorities
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
