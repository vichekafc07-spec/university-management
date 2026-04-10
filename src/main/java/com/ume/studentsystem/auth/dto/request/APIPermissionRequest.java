package com.ume.studentsystem.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record APIPermissionRequest(
        @NotBlank(message = "data must not blank")
        String httpMethod,
        @NotBlank(message = "data must not blank")
        String path,
        @NotBlank(message = "data must not blank")
        String permission
) {}
