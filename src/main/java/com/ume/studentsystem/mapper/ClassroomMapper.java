package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.request.ClassroomRequest;
import com.ume.studentsystem.dto.response.ClassroomResponse;
import com.ume.studentsystem.model.Classroom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {

    @Mapping(source = "department.name", target = "department")
    @Mapping(target = "subjects", expression = "java( classroom.getSubjects().stream().map(com.ume.studentsystem.model.Subject::getTitle).collect(java.util.stream.Collectors.toSet()))")
    ClassroomResponse toResponse(Classroom classroom);

    @Mapping(source = "departmentId" , target = "department.id")
    Classroom toEntity(ClassroomRequest classroomRequest);

    void toUpdate(ClassroomRequest request, @MappingTarget Classroom classroom);
}
