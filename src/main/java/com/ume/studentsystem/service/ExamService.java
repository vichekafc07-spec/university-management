package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.exam.ExamRequest;
import com.ume.studentsystem.dto.response.exam.ExamResponse;

import java.util.List;

public interface ExamService {

    ExamResponse create(ExamRequest request);

    List<ExamResponse> getByClassroom(Long classroomId);

    List<ExamResponse> getByInvigilator(Long staffId);

    void delete(Long id);
}
