package com.ume.studentsystem.controller.student;

import com.ume.studentsystem.dto.response.AdmissionResponse;
import com.ume.studentsystem.service.StudentAdmissionService;
import com.ume.studentsystem.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admissions")
@RequiredArgsConstructor
public class StudentAdmissionController {

    private final StudentAdmissionService admissionService;

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('staff:write')")
    public ResponseEntity<APIResponse<AdmissionResponse>> approve(@PathVariable Long id) {
        return ResponseEntity.ok(APIResponse.ok(admissionService.approve(id)));
    }

    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('staff:write')")
    public ResponseEntity<APIResponse<AdmissionResponse>> reject(@PathVariable Long id) {
        return ResponseEntity.ok(APIResponse.ok(admissionService.reject(id)));
    }
}
