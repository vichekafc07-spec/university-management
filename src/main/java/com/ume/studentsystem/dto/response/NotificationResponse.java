package com.ume.studentsystem.dto.response;

import com.ume.studentsystem.model.enums.NotificationType;
import java.time.LocalDateTime;

public record NotificationResponse(
        Long id,
        String title,
        String message,
        NotificationType type,
        Boolean isRead,
        LocalDateTime createdAt
) {
}
