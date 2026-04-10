package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.response.AdmissionResponse;
import com.ume.studentsystem.service.StudentAdmissionService;
import com.ume.studentsystem.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admissions")
@RequiredArgsConstructor
public class StudentAdmissionController {

    private final StudentAdmissionService admissionService;

    @PutMapping("/{id}/approve")
    public ResponseEntity<APIResponse<AdmissionResponse>> approve(@PathVariable Long id) {
        return ResponseEntity.ok(APIResponse.ok(admissionService.approve(id)));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<APIResponse<AdmissionResponse>> reject(@PathVariable Long id) {
        return ResponseEntity.ok(APIResponse.ok(admissionService.reject(id)));
    }
}
