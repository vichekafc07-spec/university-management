package com.ume.studentsystem.mapper;

import com.ume.studentsystem.dto.request.NotificationRequest;
import com.ume.studentsystem.dto.response.NotificationResponse;
import com.ume.studentsystem.model.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationResponse toResponse(Notification notification);

    Notification toEntity(NotificationRequest request);
}
