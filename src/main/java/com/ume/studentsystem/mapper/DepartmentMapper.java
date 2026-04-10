package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.request.DepartmentRequest;
import com.ume.studentsystem.dto.response.DepartmentResponse;
import com.ume.studentsystem.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    @Mapping(source = "faculty.name" , target = "facultyName")
    DepartmentResponse toResponse(Department department);

    @Mapping(source = "facultyId" , target = "faculty.id")
    Department toEntity(DepartmentRequest request);

    void toUpdate(DepartmentRequest request, @MappingTarget Department department);

}
