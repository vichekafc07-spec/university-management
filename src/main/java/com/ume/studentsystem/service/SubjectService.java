package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.SubjectRequest;
import com.ume.studentsystem.dto.response.SubjectResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.validation.Valid;

public interface SubjectService {
    SubjectResponse create(@Valid SubjectRequest request);

    SubjectResponse update(Long id, SubjectRequest request);

    void delete(Long id);

    PageResponse<SubjectResponse> getAllStaff(Long id, String code, String title, String department, String sortBy, String sortAs, Integer page, Integer size);
}
