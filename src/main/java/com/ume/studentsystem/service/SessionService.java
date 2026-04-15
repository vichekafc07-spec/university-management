package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.SessionRequest;
import com.ume.studentsystem.dto.response.SessionResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.validation.Valid;

public interface SessionService {
    SessionResponse create(SessionRequest request);
    PageResponse<SessionResponse> getAll(String subjectName, String roomName, String day, String sortBy, String sortAs, Integer page, Integer size);
    void delete(Long id);

    SessionResponse update(Long id, @Valid SessionRequest request);
}
