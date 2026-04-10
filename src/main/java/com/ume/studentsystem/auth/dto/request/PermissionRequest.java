package com.ume.studentsystem.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PermissionRequest(
        @NotBlank(message = "Permission name must not blank")
        String name
) {
}
