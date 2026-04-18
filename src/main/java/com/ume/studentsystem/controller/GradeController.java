package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.GradeRequest;
import com.ume.studentsystem.dto.response.GradeResponse;
import com.ume.studentsystem.service.GradeService;
import com.ume.studentsystem.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public ResponseEntity<APIResponse<GradeResponse>> create(@Valid @RequestBody GradeRequest request) {
        return ResponseEntity.ok(APIResponse.ok(gradeService.create(request)));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<APIResponse<List<GradeResponse>>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(APIResponse.ok(gradeService.getByStudent(studentId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        gradeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
