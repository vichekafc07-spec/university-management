package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.AcademicTermRequest;
import com.ume.studentsystem.dto.response.AcademicTermResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.validation.Valid;

import java.time.LocalDate;

public interface AcademicService {
    AcademicTermResponse create(AcademicTermRequest request);

    PageResponse<AcademicTermResponse> getAll(Long id, Integer year, Integer semester, LocalDate startDate, LocalDate endDate, String sortBy, String sortAs, Integer page, Integer size);

    AcademicTermResponse updateTerm(Long id, @Valid AcademicTermRequest request);

    void delete(Long id);

    String restore(Long id);
}
