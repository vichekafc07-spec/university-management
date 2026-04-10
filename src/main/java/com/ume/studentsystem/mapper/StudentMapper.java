package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.request.StudentRequest;
import com.ume.studentsystem.dto.response.StudentResponse;
import com.ume.studentsystem.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(source = "faculty.name", target = "facultyName")
    @Mapping(source = "department.name", target = "departmentName")
    StudentResponse toResponse(Student student);

    @Mapping(source = "facultyId", target = "faculty.id")
    @Mapping(source = "departmentId", target = "department.id")
    Student toEntity(StudentRequest request);

    void updateStudent(StudentRequest request, @MappingTarget Student student);
}
