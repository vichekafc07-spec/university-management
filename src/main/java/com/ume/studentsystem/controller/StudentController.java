package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.StudentRequest;
import com.ume.studentsystem.dto.response.StudentResponse;
import com.ume.studentsystem.service.StudentService;
import com.ume.studentsystem.util.APIResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<APIResponse<StudentResponse>> create(@Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(APIResponse.create(studentService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<StudentResponse>> update(@PathVariable Long id,
                                                  @Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(APIResponse.ok(studentService.update(id, request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<StudentResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(APIResponse.ok(studentService.getStudentById(id)));
    }

    @GetMapping
    public ResponseEntity<APIResponse<PageResponse<StudentResponse>>> getAll(@RequestParam(required = false) Long id,
                                                                             @RequestParam(required = false) String fullName,
                                                                             @RequestParam(required = false) String studentCode,
                                                                             @RequestParam(required = false) String faculty,
                                                                             @RequestParam(required = false) String major,
                                                                             @RequestParam(required = false) Integer generation,
                                                                             @RequestParam(required = false) String payment,
                                                                             @RequestParam(required = false) String programType,
                                                                             @RequestParam(required = false) String status,
                                                                             @RequestParam(required = false) String sortBy,
                                                                             @RequestParam(required = false) String sortAs,
                                                                             @RequestParam(required = false , defaultValue = "1") Integer page,
                                                                             @RequestParam(required = false , defaultValue = "5") Integer size) {
        return ResponseEntity.ok(APIResponse.ok(studentService.getAll(id,fullName,studentCode,faculty,major,generation,payment,programType,status,sortBy,sortAs,page,size)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
