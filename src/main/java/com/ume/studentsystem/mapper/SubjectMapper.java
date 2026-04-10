package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.request.SubjectRequest;
import com.ume.studentsystem.dto.response.SubjectResponse;
import com.ume.studentsystem.model.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    @Mapping(source = "departmentId" , target = "department.id")
    Subject toEntity(SubjectRequest request);
    @Mapping(source = "department.name" , target = "department")
    SubjectResponse toResponse(Subject subject);

    void update(SubjectRequest request, @MappingTarget Subject subject);
}
