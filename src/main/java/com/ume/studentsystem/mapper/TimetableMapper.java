package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.request.TimetableRequest;
import com.ume.studentsystem.dto.response.TimetableResponse;
import com.ume.studentsystem.model.Timetable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TimetableMapper {

    Timetable toEntity(TimetableRequest request);

    @Mapping(source = "term.year", target = "year")
    @Mapping(source = "term.semester", target = "semester")
    @Mapping(source = "term.startDate", target = "startDate")
    @Mapping(source = "term.endDate", target = "endDate")

    @Mapping(source = "classroom.name", target = "classroomName")
    @Mapping(source = "subject.title", target = "subjectTitle")
    @Mapping(source = "lecturer.fullName", target = "lecturerName")
    @Mapping(source = "room.name", target = "roomName")
    TimetableResponse toResponse(Timetable entity);
}
