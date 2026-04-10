package com.ume.studentsystem.controller;

import com.ume.studentsystem.auth.dto.request.*;
import com.ume.studentsystem.auth.dto.response.*;
import com.ume.studentsystem.service.AdminService;
import com.ume.studentsystem.util.APIResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/role")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<RoleResponse>> addRole(@Valid @RequestBody RoleRequest request){
        return ResponseEntity.ok(APIResponse.create(adminService.createRole(request)));
    }

    @GetMapping("/role")
    @PreAuthorize("hasAnyAuthority('user:write','user:read')")
    public ResponseEntity<APIResponse<List<RoleResponse>>> getRole(){
        return ResponseEntity.ok(APIResponse.ok(adminService.getAllRole()));
    }

    @PutMapping("/role/{roleId}/permission")
    @PreAuthorize("hasAnyAuthority('user:write','user:read')")
    public ResponseEntity<APIResponse<RolePermissionResponse>> assignPermission(@PathVariable Integer roleId , @Valid @RequestBody RolePermissionRequest request){
        return ResponseEntity.ok(APIResponse.ok(adminService.assignPermission(roleId,request)));
    }

    @PostMapping("/permission")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<PermissionResponse>> createPermission(@Valid @RequestBody PermissionRequest request){
        return ResponseEntity.ok(APIResponse.create(adminService.addPermission(request)));
    }

    @GetMapping("/permission")
    @PreAuthorize("hasAnyAuthority('user:write','user:read')")
    public ResponseEntity<APIResponse<PageResponse<PermissionResponse>>> getAll(@RequestParam(required = false) Long id,
                                                                           @RequestParam(required = false) String sortBy,
                                                                           @RequestParam(required = false) String sortAs,
                                                                           @RequestParam(required = false , defaultValue = "1") Integer page,
                                                                           @RequestParam(required = false , defaultValue = "5") Integer size) {
        PageResponse<PermissionResponse> pageResponse = adminService.getAllPermission(id,sortBy,sortAs,page,size);
        return ResponseEntity.ok(APIResponse.ok(pageResponse));
    }

    @PutMapping("/user/{userId}/role")
    @PreAuthorize("hasAnyAuthority('user:write','user:read')")
    public ResponseEntity<APIResponse<UserRoleResponse>> assignRole(@PathVariable Long userId , @Valid @RequestBody UserRoleRequest request){
        return ResponseEntity.ok(APIResponse.ok(adminService.assignRole(userId,request)));
    }

    @DeleteMapping("/role/{roleId}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<RoleResponse>> deleteRole(@PathVariable Integer roleId){
        return ResponseEntity.ok(APIResponse.ok(adminService.deleteRole(roleId)));
    }

    @DeleteMapping("/permission/{permissionId}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<PermissionResponse>> deletePermission(@PathVariable Integer permissionId){
        return ResponseEntity.ok(APIResponse.ok(adminService.deletePermission(permissionId)));
    }

    @PostMapping("/api-permission")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<APIPermissionResponse>> create(@Valid @RequestBody APIPermissionRequest req) {
        return ResponseEntity.ok(APIResponse.create(adminService.create(req)));
    }

    @PutMapping("/api-permission/update/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<APIPermissionResponse>> update(@PathVariable Integer id, @Valid @RequestBody APIPermissionRequest req) {
        return ResponseEntity.ok(APIResponse.ok(adminService.update(id, req)));
    }

    @GetMapping("/api-permission")
    @PreAuthorize("hasAnyAuthority('user:write','user:read')")
    public ResponseEntity<APIResponse<List<APIPermissionResponse>>> findAll() {
        return ResponseEntity.ok(APIResponse.ok(adminService.findAll()));
    }

    @DeleteMapping("/api-permission/delete/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        adminService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}