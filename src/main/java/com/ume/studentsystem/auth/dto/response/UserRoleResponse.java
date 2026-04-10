package com.ume.studentsystem.auth.dto.response;

import java.util.Set;

public record UserRoleResponse(Long userId , Set<String> role) {
}
