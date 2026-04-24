package com.ume.studentsystem.dto.response;

import com.ume.studentsystem.model.Grade;
import com.ume.studentsystem.model.StudentSubject;

public record TranscriptRow(
        StudentSubject studentSubject,
        Grade grade
) {
}
