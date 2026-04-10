package com.ume.studentsystem.dto.response;

import com.ume.studentsystem.model.enums.StudyTime;

import java.util.Set;

public record ClassroomResponse(
        Long id,
        String name,
        int year,
        int semester,
        int generation,
        String department,
        StudyTime time,
        Set<String> subjects
) {}
