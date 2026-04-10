package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.auth.dto.request.*;
import com.ume.studentsystem.auth.dto.response.*;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.AdminMapper;
import com.ume.studentsystem.model.Permission;
import com.ume.studentsystem.model.Role;
import com.ume.studentsystem.repository.APIPermissionRepository;
import com.ume.studentsystem.repository.PermissionRepository;
import com.ume.studentsystem.repository.RoleRepository;
import com.ume.studentsystem.repository.UserRepository;
import com.ume.studentsystem.service.AdminService;
import com.ume.studentsystem.spec.SpecificationBuilder;
import com.ume.studentsystem.util.PageResponse;
import com.ume.studentsystem.util.SortResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final APIPermissionRepository apiPermissionRepository;
    private final AdminMapper adminMapper;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        roleRepository.findByName(request.name()).ifPresent(e -> {
            throw new BadRequestException("Role name is already exists");
        });
        var role = new Role();
        role.setName(request.name());
        roleRepository.save(role);
        return adminMapper.responseRole(role);
    }

    @Override
    public List<RoleResponse> getAllRole() {
        return roleRepository.findAll()
                .stream()
                .map(adminMapper::responseRole)
                .toList();
    }

    @Override
    public RolePermissionResponse assignPermission(Integer roleId, RolePermissionRequest request) {
        var role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
        var permission = new HashSet<>(permissionRepository.findAllById(request.permissionIds()));
        if (permission.isEmpty()){
            throw new BadRequestException("No valid permissions found");
        }
        role.setPermissions(permission);
        roleRepository.save(role);

        Set<String> permissionName = permission.stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());

        return new RolePermissionResponse(role.getName(),permissionName);
    }

    @Override
    public PermissionResponse addPermission(PermissionRequest request) {
        permissionRepository.findByName(request.name()).ifPresent(e -> {
            throw new BadRequestException("Permission name is already exists");
        });
        var permission = new Permission();
        permission.setName(request.name());
        permissionRepository.save(permission);
        return adminMapper.toResponse(permission);
    }

    @Override
    public PageResponse<PermissionResponse> getAllPermission(Long id, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<Permission> spec = new SpecificationBuilder<Permission>()
                .equal("id", id)
                .build();
        List<String> allowSort = List.of("id");
        Sort sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page - 1,size,sort);
        Page<Permission> permissionPage = permissionRepository.findAll(spec,pageable);

        return PageResponse.from(permissionPage, adminMapper::toResponse);
    }

    @Override
    public UserRoleResponse assignRole(Long userId, UserRoleRequest request) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        var role = new HashSet<>(roleRepository.findAllById(request.roleIds()));
        if (role.isEmpty()){
            throw new BadRequestException("No valid role found");
        }
        user.setRoles(role);
        userRepository.save(user);

        Set<String> roleName = role.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new UserRoleResponse(user.getId(),roleName);
    }

    @Override
    public RoleResponse deleteRole(Integer roleId) {
        var role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
        roleRepository.delete(role);
        return adminMapper.responseRole(role);
    }

    @Override
    public PermissionResponse deletePermission(Integer permissionId) {
        var permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id " + permissionId));
        permissionRepository.delete(permission);
        return adminMapper.toResponse(permission);
    }

    @Override
    public APIPermissionResponse create(APIPermissionRequest request) {
        var permission = adminMapper.toEntityPermission(request);
        permission.setHttpMethod(request.httpMethod().toUpperCase());
        var saved = apiPermissionRepository.save(permission);
        return adminMapper.responsePermission(saved);
    }

    @Override
    public APIPermissionResponse update(Integer id, APIPermissionRequest request) {
        var permission = apiPermissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("API Permission not found"));
        adminMapper.updateAPIPermission(request,permission);
        var saved = apiPermissionRepository.save(permission);
        return adminMapper.responsePermission(saved);
    }

    @Override
    public List<APIPermissionResponse> findAll() {
        return apiPermissionRepository.findAll()
                .stream()
                .map(adminMapper::responsePermission)
                .toList();
    }

    @Override
    public void delete(Integer id) {
        var permission = apiPermissionRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("API Permission not found"));
        apiPermissionRepository.delete(permission);
    }

}