package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.response.StudentClassroomResponse;
import com.ume.studentsystem.model.StudentClassroom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentClassroomMapper {

    @Mapping(source = "student.fullName", target = "studentName")
    @Mapping(source = "student.studentCode", target = "studentCode")

    @Mapping(source = "classroom.name", target = "classroomName")
    @Mapping(source = "classroom.year", target = "year")
    @Mapping(source = "classroom.semester", target = "semester")
    @Mapping(source = "classroom.time" , target = "time")
    @Mapping(source = "classroom.generation" , target = "generation")
    StudentClassroomResponse toResponse(StudentClassroom entity);

}
