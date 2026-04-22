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
import java.util.Set;

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

    @PostMapping("/auto/{studentSubjectId}")
    public ResponseEntity<APIResponse<GradeResponse>> autoCalculate(@PathVariable Long studentSubjectId){
        return ResponseEntity.ok(APIResponse.ok(gradeService.autoCalculate(studentSubjectId)));
    }

    @PostMapping("/auto-bulk")
    public ResponseEntity<APIResponse<List<GradeResponse>>> bulk(@RequestBody Set<Long> ids){
        return ResponseEntity.ok(APIResponse.ok(gradeService.autoCalculateBulk(ids)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        gradeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
