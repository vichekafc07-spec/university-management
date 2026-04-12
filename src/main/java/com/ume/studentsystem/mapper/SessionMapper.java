package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.response.SessionResponse;
import com.ume.studentsystem.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    @Mapping(source = "lecturerAssignment.lecturer.fullName", target = "lecturerName")
    @Mapping(source = "lecturerAssignment.subject.title", target = "subjectName")
    @Mapping(source = "lecturerAssignment.classroom.name", target = "classroomName")
    @Mapping(source = "room.name", target = "roomName")
    SessionResponse toResponse(Session session);
}
