package com.ume.studentsystem.auth.service;

import com.ume.studentsystem.auth.dto.request.AuthRequest;
import com.ume.studentsystem.auth.dto.request.ChangePassword;
import com.ume.studentsystem.auth.dto.request.UserRequest;
import com.ume.studentsystem.auth.dto.response.AuthResponse;
import com.ume.studentsystem.auth.dto.response.JwtResponse;
import com.ume.studentsystem.auth.jwt.JwtService;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.DuplicateResourceException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.UserMapper;
import com.ume.studentsystem.model.AppUser;
import com.ume.studentsystem.model.Role;
import com.ume.studentsystem.repository.RoleRepository;
import com.ume.studentsystem.repository.UserRepository;
import com.ume.studentsystem.util.PageResponse;
import com.ume.studentsystem.util.SortResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse createUser(UserRequest request){
       if (userRepository.existsByEmail(request.email())){
           throw new DuplicateResourceException("Email already exist");
        }
       var user = userMapper.toEntity(request);
        Set<Role> roles = request.role().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName))
                )
                .collect(Collectors.toSet());
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(request.password()));
       return userMapper.toResponse(userRepository.save(user));
    }

    public PageResponse<AuthResponse> getAllUsers(Long id, String username, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<AppUser> spec = Specification.unrestricted();
        if (id != null) {
            spec = spec.and(((root, query, cb) ->
                    cb.equal(root.get("id"), id)));
        }
        if (username != null && !username.isEmpty()) {
            spec = spec.and(((root, query, cb) ->
                    cb.like(cb.lower(root.get("username")),'%'+ username + '%')));
        }
        List<String> allowSort = List.of("id","username");
        var sort = SortResponse.sortResponse(sortBy, sortAs, allowSort);
        Pageable pageable = PageRequest.of(page - 1,size,sort);
        Page<AppUser> userPage = userRepository.findAll(spec,pageable);
        return PageResponse.from(userPage,userMapper::toResponse);
    }

    public void changePassword(Long id , ChangePassword request){
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())){
            throw new BadRequestException("Old password not found");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    public JwtResponse login(AuthRequest request, HttpServletResponse response){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = userRepository.findByEmailWithRoles(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        var cookie = new Cookie("refreshToken",refreshToken.generate());
        cookie.setPath("/api/v1/auth/refresh");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(604800);
        cookie.setSecure(true);
        response.addCookie(cookie);

        return new JwtResponse(accessToken.generate());
    }

    public JwtResponse refreshToken(String refreshToken){
        var jwt = jwtService.parseToken(refreshToken);
        if (jwt == null || jwt.isExpiration()){
            throw new RuntimeException("Expiration");
        }
        var user = userRepository.findById(jwt.getUserId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var accessToken = jwtService.generateAccessToken(user);
        return new JwtResponse(accessToken.generate());
    }

    public AuthResponse me(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toResponse(user);
    }
}
