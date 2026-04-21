package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.request.ExamRequest;
import com.ume.studentsystem.dto.response.ExamResponse;
import com.ume.studentsystem.model.Exam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamMapper {

    @Mapping(source = "termId" , target = "term.id")
    @Mapping(source = "roomId" , target = "room.id")
    @Mapping(source = "classroomId" , target = "classroom.id")
    @Mapping(source = "subjectId" , target = "subject.id")
    @Mapping(source = "invigilatorId" , target = "invigilator.id")
    Exam toEntity(ExamRequest request);

    @Mapping(source = "term.year", target = "year")
    @Mapping(source = "term.semester", target = "semester")
    @Mapping(source = "classroom.name", target = "classroomName")
    @Mapping(source = "subject.title", target = "subjectTitle")
    @Mapping(source = "room.name", target = "roomName")
    @Mapping(source = "invigilator.fullName", target = "invigilatorName")
    ExamResponse toResponse(Exam exam);
}
