package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.TimetableRequest;
import com.ume.studentsystem.dto.response.TimetableResponse;
import java.util.List;

public interface TimetableService {

    TimetableResponse create(TimetableRequest request);

    List<TimetableResponse> getByClassroom(Long classroomId);

    List<TimetableResponse> getByLecturer(Long lecturerId);

    void delete(Long id);
}
