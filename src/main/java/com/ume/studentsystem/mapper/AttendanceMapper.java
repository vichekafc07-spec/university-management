package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.response.AttendanceResponse;
import com.ume.studentsystem.model.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mapping(source = "studentSubject.studentClassroom.student.fullName", target = "studentName")
    @Mapping(source = "studentSubject.subject.title", target = "subjectTitle")
    @Mapping(source = "studentSubject.classroom.name", target = "classroomName")
    @Mapping(source = "session.lecturerAssignment.lecturer.fullName", target = "lecturerName")
    @Mapping(source = "session.room.name", target = "room")
    @Mapping(source = "session.day", target = "day")
    @Mapping(source = "session.startTime", target = "startTime")
    @Mapping(source = "session.endTime", target = "endTime")
    AttendanceResponse toResponse(Attendance attendance);
}
