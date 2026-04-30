package com.ume.studentsystem.model;

import com.ume.studentsystem.model.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private Boolean isRead = false;

    private LocalDateTime createdAt;
}
