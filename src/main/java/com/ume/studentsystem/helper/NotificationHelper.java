package com.ume.studentsystem.helper;

import com.ume.studentsystem.model.Notification;
import com.ume.studentsystem.model.enums.NotificationType;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class NotificationHelper {
    public static Notification send(Long userId, String title, String message, NotificationType type) {
        return Notification.builder()
                .userId(userId)
                .title(title)
                .message(message)
                .type(type)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

}
