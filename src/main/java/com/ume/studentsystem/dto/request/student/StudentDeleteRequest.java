package com.ume.studentsystem.dto.request.student;

import java.util.Set;

public record StudentDeleteRequest(Set<Long> studentIds) {
}
