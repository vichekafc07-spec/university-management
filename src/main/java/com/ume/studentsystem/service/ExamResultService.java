package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.ExamResultBulkRequest;
import com.ume.studentsystem.dto.request.ExamResultRequest;
import com.ume.studentsystem.dto.response.ExamResultResponse;

import java.util.List;

public interface ExamResultService {

    ExamResultResponse save(ExamResultRequest request);

    List<ExamResultResponse> saveBulk(ExamResultBulkRequest request);

    List<ExamResultResponse> getByExam(Long examId);

    List<ExamResultResponse> getByStudentSubject(Long studentSubjectId);

    void delete(Long id);
}
