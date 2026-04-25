package com.ume.studentsystem.dto.request;

import com.ume.studentsystem.model.enums.NotificationType;

public record NotificationRequest(
        Long userId,
        String title,
        String message,
        NotificationType type
) {
}
