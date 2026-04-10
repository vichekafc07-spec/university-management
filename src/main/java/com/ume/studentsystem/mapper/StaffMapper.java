package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.request.StaffRequest;
import com.ume.studentsystem.dto.request.StaffUpdateRequest;
import com.ume.studentsystem.dto.response.StaffResponse;
import com.ume.studentsystem.model.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StaffMapper {

    @Mapping(source = "userId" , target = "user.id")
    @Mapping(source = "facultyId" , target = "faculty.id")
    @Mapping(source = "departmentId" , target = "department.id")
    @Mapping(source = "personalEmail", target = "personalEmail")
    Staff toEntity(StaffRequest request);

    @Mapping(source = "faculty.name" , target = "faculty")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "department.name" , target = "department")
    @Mapping(source = "personalEmail" , target = "personalEmail")
    StaffResponse toResponse(Staff staff);

    void updateStaff(StaffUpdateRequest request, @MappingTarget Staff staff);

}
