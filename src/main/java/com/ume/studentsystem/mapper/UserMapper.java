package com.ume.studentsystem.mapper;

import com.ume.studentsystem.auth.dto.response.AuthResponse;
import com.ume.studentsystem.auth.dto.request.UserRequest;
import com.ume.studentsystem.model.AppUser;
import com.ume.studentsystem.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "roles" , ignore = true)
    AppUser toEntity(UserRequest request);

    @Mapping(source = "roles" , target = "roles")
    AuthResponse toResponse(AppUser user);

    default String map(Role role) {
        return role.getName();
    }

    default Set<String> map(Set<Role> roles) {
        return roles.stream()
                .map(this::map)
                .collect(Collectors.toSet());
    }

}