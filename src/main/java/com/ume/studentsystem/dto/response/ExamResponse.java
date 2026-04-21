package com.ume.studentsystem.dto.response;

import com.ume.studentsystem.model.enums.ExamType;
import java.time.LocalDate;
import java.time.LocalTime;

public record ExamResponse(

        Long id,

        Integer year,
        Integer semester,

        String classroomName,
        String subjectTitle,
        String roomName,
        String invigilatorName,

        ExamType examType,
        LocalDate examDate,
        LocalTime startTime,
        LocalTime endTime,

        Integer totalMarks
) {
}
