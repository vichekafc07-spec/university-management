package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.NotificationRequest;
import com.ume.studentsystem.dto.response.NotificationResponse;
import com.ume.studentsystem.service.NotificationService;
import com.ume.studentsystem.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public ResponseEntity<APIResponse<NotificationResponse>> create(@RequestBody NotificationRequest request) {
        return ResponseEntity.ok(APIResponse.ok(service.create(request)));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<APIResponse<List<NotificationResponse>>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(APIResponse.ok(service.getByUser(userId)));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<APIResponse<String>> read(@PathVariable Long id) {
        service.markRead(id);
        return ResponseEntity.ok(APIResponse.ok("Marked as read"));
    }

    @GetMapping("/users/{userId}/unread-count")
    public ResponseEntity<APIResponse<Long>> unread(@PathVariable Long userId) {
        return ResponseEntity.ok(APIResponse.ok(service.unreadCount(userId)));
    }
}
