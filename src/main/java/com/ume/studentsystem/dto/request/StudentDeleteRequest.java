package com.ume.studentsystem.dto.request;

import java.util.Set;

public record StudentDeleteRequest(Set<Long> studentIds) {
}
