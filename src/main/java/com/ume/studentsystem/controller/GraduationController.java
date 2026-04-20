package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.response.GraduationResponse;
import com.ume.studentsystem.service.GraduationService;
import com.ume.studentsystem.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/graduation")
@RequiredArgsConstructor
public class GraduationController {

    private final GraduationService graduationService;

    @GetMapping("/{studentId}")
    public ResponseEntity<APIResponse<GraduationResponse>> check(@PathVariable Long studentId
    ) {
        return ResponseEntity.ok(APIResponse.ok(graduationService.checkEligibility(studentId)));
    }
}
