package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.AssignSubjectRequest;
import com.ume.studentsystem.dto.request.ClassroomRequest;
import com.ume.studentsystem.dto.response.ClassroomResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.validation.Valid;

public interface ClassroomService {
    ClassroomResponse create(ClassroomRequest request);
    ClassroomResponse addSubject(Long classroomId , AssignSubjectRequest subjects);
    ClassroomResponse removeSubject(Long classroomId, AssignSubjectRequest request);
    void delete(Long id);

    PageResponse<ClassroomResponse> getAllClassroom(Long id, String name, Integer year, Integer semester, Integer generation, String time, String sortBy, String sortAs, Integer page, Integer size);

    ClassroomResponse updateClassroom(Long id, @Valid ClassroomRequest request);

    String restore(Long id);
}
