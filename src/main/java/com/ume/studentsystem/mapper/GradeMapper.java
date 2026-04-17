package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.request.GradeRequest;
import com.ume.studentsystem.dto.response.GradeResponse;
import com.ume.studentsystem.model.Grade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    @Mapping(source = "studentSubject.studentClassroom.student.fullName", target = "studentName")
    @Mapping(source = "studentSubject.subject.title", target = "subjectTitle")
    GradeResponse toResponse(Grade grade);

    @Mapping(source = "studentSubjectId" , target = "studentSubject.id")
    Grade toEntity(GradeRequest request);
}
