package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.response.DashboardResponse;
import com.ume.studentsystem.service.DashboardService;
import com.ume.studentsystem.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboards")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<APIResponse<DashboardResponse>> getDashboard() {
        return ResponseEntity.ok(APIResponse.ok(dashboardService.getDashboard()));
    }
}
