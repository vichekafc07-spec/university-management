package com.ume.studentsystem.auth.dto.request;

import java.util.Set;

public record UserRequest(String username , String email, String password , Set<String> role) {
}
