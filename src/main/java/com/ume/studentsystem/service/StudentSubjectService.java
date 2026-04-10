package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.AssignStudentSubjectRequest;
import com.ume.studentsystem.dto.response.StudentSubjectResponse;
import java.util.List;

public interface StudentSubjectService {
    List<StudentSubjectResponse> assignSubjects(AssignStudentSubjectRequest request);

    List<StudentSubjectResponse> getSubjectsByStudent(Long studentId);

    List<StudentSubjectResponse> getStudentBySubject(Long subjectId);
}
