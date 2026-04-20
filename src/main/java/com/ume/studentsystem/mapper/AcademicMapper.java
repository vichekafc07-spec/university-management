package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.request.AcademicTermRequest;
import com.ume.studentsystem.dto.response.AcademicTermResponse;
import com.ume.studentsystem.model.AcademicTerm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AcademicMapper {
    AcademicTerm toEntity(AcademicTermRequest request);
    AcademicTermResponse toResponse(AcademicTerm academicTerm);
    void updateTerm(AcademicTermRequest request, @MappingTarget AcademicTerm academicTerm);
}
