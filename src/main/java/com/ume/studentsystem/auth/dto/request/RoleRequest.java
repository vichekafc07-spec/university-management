package com.ume.studentsystem.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(
        @NotBlank(message = "Role name must not blank")
        String name
) {
}
