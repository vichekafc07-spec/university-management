package com.ume.studentsystem.auth.dto.response;

public record APIPermissionResponse(
        Integer id,
        String httpMethod,
        String path,
        String permission
) {}
