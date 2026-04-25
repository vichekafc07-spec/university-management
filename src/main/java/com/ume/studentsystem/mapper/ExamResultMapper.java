package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.response.exam.ExamResultResponse;
import com.ume.studentsystem.model.ExamResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamResultMapper {

    @Mapping(source = "studentSubject.studentClassroom.student.fullName", target = "studentName")
    @Mapping(source = "studentSubject.studentClassroom.student.studentCode", target = "studentCode")
    @Mapping(source = "studentSubject.subject.title", target = "subjectTitle")
    @Mapping(source = "exam.examType", target = "examType")
    @Mapping(source = "exam.examDate", target = "examDate")
    ExamResultResponse toResponse(ExamResult entity);
}
