package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.exam.ExamRequest;
import com.ume.studentsystem.dto.response.exam.ExamResponse;
import com.ume.studentsystem.service.ExamService;
import com.ume.studentsystem.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping
    public ResponseEntity<APIResponse<ExamResponse>> create(@Valid @RequestBody ExamRequest request) {
        return ResponseEntity.ok(APIResponse.ok(examService.create(request)));
    }

    @GetMapping("/classroom/{id}")
    public ResponseEntity<APIResponse<List<ExamResponse>>> getByClassroom(@PathVariable Long id) {
        return ResponseEntity.ok(APIResponse.ok(examService.getByClassroom(id)));
    }

    @GetMapping("/invigilator/{id}")
    public ResponseEntity<APIResponse<List<ExamResponse>>> getByInvigilator(@PathVariable Long id) {
        return ResponseEntity.ok(APIResponse.ok(examService.getByInvigilator(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        examService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
