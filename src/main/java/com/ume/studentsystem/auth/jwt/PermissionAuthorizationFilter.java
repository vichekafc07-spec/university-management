package com.ume.studentsystem.auth.jwt;

import com.ume.studentsystem.model.APIPermission;
import com.ume.studentsystem.repository.APIPermissionRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PermissionAuthorizationFilter extends OncePerRequestFilter {

    private final APIPermissionRepository apiPermissionRepository;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {

        String method = request.getMethod();
        String path = normalizePath(request.getRequestURI());

        List<APIPermission> apiPermissions =
                apiPermissionRepository.findAllByPathAndMethod(method, path);

        if (apiPermissions.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        List<String> requiredPermissions = apiPermissions.stream()
                .map(APIPermission::getPermission)
                .toList();

        String token = jwtService.extractToken(request);

        if (token == null) {
            denyAccess(response, "Missing token");
            return;
        }

        Set<String> userPermissions = jwtService.extractPermissions(token);

        boolean authorized = userPermissions.stream()
                .anyMatch(requiredPermissions::contains);

        if (!authorized) {
            denyAccess(response,
                    "Missing permission. Required: " + requiredPermissions);
            return;
        }

        chain.doFilter(request, response);
    }

    private void denyAccess(HttpServletResponse response, String message)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }

    private String normalizePath(String path) {
        return path.replaceAll("/\\d+", "/{id}");
    }
}