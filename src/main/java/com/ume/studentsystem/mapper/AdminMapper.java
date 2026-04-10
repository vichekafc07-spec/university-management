package com.ume.studentsystem.mapper;

import com.ume.studentsystem.auth.dto.request.APIPermissionRequest;
import com.ume.studentsystem.auth.dto.response.APIPermissionResponse;
import com.ume.studentsystem.auth.dto.response.PermissionResponse;
import com.ume.studentsystem.auth.dto.response.RoleResponse;
import com.ume.studentsystem.model.APIPermission;
import com.ume.studentsystem.model.Permission;
import com.ume.studentsystem.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    PermissionResponse toResponse(Permission permission);
    RoleResponse responseRole(Role role);
    APIPermissionResponse responsePermission(APIPermission apiPermission);
    APIPermission toEntityPermission(APIPermissionRequest request);
    void updateAPIPermission(APIPermissionRequest request, @MappingTarget APIPermission apiPermission);
}
