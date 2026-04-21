package com.ume.studentsystem.dto.request;

import com.ume.studentsystem.model.enums.ExamType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ExamRequest(

        @NotNull(message = "term id required")
        Long termId,

        @NotNull(message = "classroom id required")
        Long classroomId,

        @NotNull(message = "subject id required")
        Long subjectId,

        @NotNull(message = "room id required")
        Long roomId,

        @NotNull(message = "invigilator id required")
        Long invigilatorId,

        @NotNull(message = "exam type required")
        ExamType examType,

        @NotNull(message = "term date required")
        LocalDate examDate,

        @NotNull(message = "start time required")
        LocalTime startTime,

        @NotNull(message = "end time required")
        LocalTime endTime,

        @NotNull(message = "total mark required")
        Integer totalMarks
) {
}