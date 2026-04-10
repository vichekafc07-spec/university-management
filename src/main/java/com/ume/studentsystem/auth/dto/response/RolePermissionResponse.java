package com.ume.studentsystem.auth.dto.response;

import java.util.Set;

public record RolePermissionResponse(String role , Set<String> permission) {
}
