package com.ume.studentsystem.auth.dto.request;

import java.util.Set;

public record UserRoleRequest(Set<Integer> roleIds) {
}
