package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.GradeRequest;
import com.ume.studentsystem.dto.response.GradeResponse;

import java.util.List;
import java.util.Set;

public interface GradeService {
    GradeResponse create(GradeRequest request);
    List<GradeResponse> getByStudent(Long studentId);
    void delete(Long id);
    GradeResponse autoCalculate(Long studentSubjectId);
    List<GradeResponse> autoCalculateBulk(Set<Long> studentSubjectIds);
}
