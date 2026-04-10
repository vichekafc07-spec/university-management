package com.ume.studentsystem.service;

import com.ume.studentsystem.auth.dto.request.*;
import com.ume.studentsystem.auth.dto.response.*;
import com.ume.studentsystem.util.PageResponse;

import java.util.List;

public interface AdminService {
    RoleResponse createRole(RoleRequest request);

    List<RoleResponse> getAllRole();

    RolePermissionResponse assignPermission(Integer roleId, RolePermissionRequest request);

    PermissionResponse addPermission(PermissionRequest request);

    PageResponse<PermissionResponse> getAllPermission(Long id, String sortBy, String sortAs, Integer page, Integer size);

    UserRoleResponse assignRole(Long userId, UserRoleRequest request);

    RoleResponse deleteRole(Integer roleId);

    PermissionResponse deletePermission(Integer permissionId);

    List<APIPermissionResponse> findAll();

    APIPermissionResponse create(APIPermissionRequest request);

    APIPermissionResponse update(Integer id , APIPermissionRequest request);

    void delete(Integer id);

}
