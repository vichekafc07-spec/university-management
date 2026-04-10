package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.response.AdmissionResponse;

public interface StudentAdmissionService {
    AdmissionResponse approve(Long studentId);
    AdmissionResponse reject(Long studentId);
}
