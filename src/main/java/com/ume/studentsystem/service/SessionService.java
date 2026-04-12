package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.SessionRequest;
import com.ume.studentsystem.dto.response.SessionResponse;

import java.util.List;

public interface SessionService {
    SessionResponse create(SessionRequest request);
    List<SessionResponse> getAll();
    void delete(Long id);
}
