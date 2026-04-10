package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.response.LecturerAssignmentResponse;
import com.ume.studentsystem.model.LecturerAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LecturerMapper {

    @Mapping(source = "lecturer.fullName", target = "lecturerName")
    @Mapping(source = "lecturer.staffCode", target = "lecturerCode")
    @Mapping(source = "classroom.name", target = "classroomName")
    @Mapping(source = "subject.title", target = "subjectName")
    LecturerAssignmentResponse toResponse(LecturerAssignment entity);
}
