package com.ume.studentsystem.auth.controller;

import com.ume.studentsystem.auth.dto.request.AuthRequest;
import com.ume.studentsystem.auth.dto.request.ChangePassword;
import com.ume.studentsystem.auth.dto.request.UserRequest;
import com.ume.studentsystem.auth.dto.response.AuthResponse;
import com.ume.studentsystem.auth.dto.response.JwtResponse;
import com.ume.studentsystem.auth.jwt.JwtService;
import com.ume.studentsystem.auth.service.AuthService;
import com.ume.studentsystem.model.BlackListedToken;
import com.ume.studentsystem.repository.BlackListedTokenRepository;
import com.ume.studentsystem.util.APIResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final BlackListedTokenRepository blackListedTokenRepository;
    private final JwtService jwtService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<AuthResponse>> create(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(APIResponse.create(authService.createUser(userRequest)));
    }

    @GetMapping("/user")
    public ResponseEntity<APIResponse<PageResponse<AuthResponse>>> getAllUser(@RequestParam(required = false) Long id,
                                                                              @RequestParam(required = false) String username,
                                                                              @RequestParam(required = false) String sortBy,
                                                                              @RequestParam(required = false) String sortAs,
                                                                              @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                              @RequestParam(required = false, defaultValue = "5") Integer size
                                                                             ){
        PageResponse<AuthResponse> pageResponse = authService.getAllUsers(id,username,sortBy,sortAs,page,size);
        return ResponseEntity.ok(APIResponse.ok(pageResponse));
    }

    @PutMapping("/{id}/change-password")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<?>> changePassword(@PathVariable Long id,
                                                         @Valid @RequestBody ChangePassword password){
        authService.changePassword(id,password);
        return ResponseEntity.ok(APIResponse.ok("Password Changed"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthRequest authRequest , HttpServletResponse response){
        return ResponseEntity.ok(authService.login(authRequest,response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue("refreshToken") String refreshToken){
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> me(){
        return ResponseEntity.ok(authService.me());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader("Authorization") String authHeader,
            HttpServletResponse response
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return ResponseEntity.badRequest().body("No token provided");

        String token = authHeader.substring(7);

        var claims = jwtService.parseToken(token);
        if (claims == null) return ResponseEntity.badRequest().body("Invalid token");

        // add to blacklist
        blackListedTokenRepository.save(
                new BlackListedToken(null, token, claims.getExpiration())
        );

        // remove refresh token cookie
        Cookie clear = new Cookie("refreshToken", null);
        clear.setPath("/api/v1/auth/refresh");
        clear.setHttpOnly(true);
        clear.setSecure(true);
        clear.setMaxAge(0);
        response.addCookie(clear);

        return ResponseEntity.ok("Logout successful");
    }

}
