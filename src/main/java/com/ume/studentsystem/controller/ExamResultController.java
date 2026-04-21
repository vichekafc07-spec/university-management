package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.ExamResultBulkRequest;
import com.ume.studentsystem.dto.request.ExamResultRequest;
import com.ume.studentsystem.dto.response.ExamResultResponse;
import com.ume.studentsystem.service.ExamResultService;
import com.ume.studentsystem.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exam-results")
@RequiredArgsConstructor
public class ExamResultController {

    private final ExamResultService examResultService;

    @PostMapping
    public ResponseEntity<APIResponse<ExamResultResponse>> save(@Valid @RequestBody ExamResultRequest request) {
        return ResponseEntity.ok(APIResponse.ok(examResultService.save(request)));
    }

    @PostMapping("/bulk")
    public ResponseEntity<APIResponse<List<ExamResultResponse>>> saveBulk(@Valid @RequestBody ExamResultBulkRequest request) {
        return ResponseEntity.ok(APIResponse.ok(examResultService.saveBulk(request)));
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<APIResponse<List<ExamResultResponse>>> byExam(@PathVariable Long examId) {
        return ResponseEntity.ok(APIResponse.ok(examResultService.getByExam(examId)));
    }

    @GetMapping("/student-subject/{id}")
    public ResponseEntity<APIResponse<List<ExamResultResponse>>> byStudent(@PathVariable Long id) {
        return ResponseEntity.ok(APIResponse.ok(examResultService.getByStudentSubject(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        examResultService.delete(id);
        return ResponseEntity.ok(APIResponse.ok("Deleted successfully"));
    }
}
