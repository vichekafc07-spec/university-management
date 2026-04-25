package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.response.student.StudentSubjectResponse;
import com.ume.studentsystem.model.StudentSubject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentSubjectMapper {

    @Mapping(source = "studentClassroom.student.fullName", target = "studentName")
    @Mapping(source = "studentClassroom.student.studentCode", target = "studentCode")

    @Mapping(source = "subject.title", target = "subjectTitle")
    @Mapping(source = "studentClassroom.classroom.name", target = "classroomName")
    @Mapping(source = "studentClassroom.classroom.semester" , target = "semester")
    StudentSubjectResponse toResponse(StudentSubject entity);
}