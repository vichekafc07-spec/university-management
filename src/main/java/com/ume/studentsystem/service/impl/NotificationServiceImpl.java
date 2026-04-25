package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.NotificationRequest;
import com.ume.studentsystem.dto.response.NotificationResponse;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.NotificationMapper;
import com.ume.studentsystem.model.Notification;
import com.ume.studentsystem.repository.NotificationRepository;
import com.ume.studentsystem.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public NotificationResponse create(NotificationRequest request) {
        var not = notificationMapper.toEntity(request);
        not.setIsRead(true);
        not.setCreatedAt(LocalDateTime.now());
        var saved = notificationRepository.save(not);

        return notificationMapper.toResponse(saved);
    }

    public List<NotificationResponse> getByUser(Long userId) {
        return notificationRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    public Long unreadCount(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    public void markRead(Long id) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        n.setIsRead(true);
    }

}
