package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.response.GraduationResponse;

public interface GraduationService {

    GraduationResponse checkEligibility(Long studentId);
}
