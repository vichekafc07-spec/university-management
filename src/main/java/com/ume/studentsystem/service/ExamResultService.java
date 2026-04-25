package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.exam.ExamResultBulkRequest;
import com.ume.studentsystem.dto.request.exam.ExamResultRequest;
import com.ume.studentsystem.dto.response.exam.ExamResultResponse;

import java.util.List;

public interface ExamResultService {

    ExamResultResponse save(ExamResultRequest request);

    List<ExamResultResponse> saveBulk(ExamResultBulkRequest request);

    List<ExamResultResponse> getByExam(Long examId);

    List<ExamResultResponse> getByStudentSubject(Long studentSubjectId);

    void delete(Long id);
}
