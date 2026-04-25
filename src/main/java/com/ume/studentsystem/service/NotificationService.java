package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.NotificationRequest;
import com.ume.studentsystem.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {
    NotificationResponse create(NotificationRequest request);
    List<NotificationResponse> getByUser(Long userId);
    Long unreadCount(Long id);
    void markRead(Long id);
}
