package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.AssignStudentSubjectRequest;
import com.ume.studentsystem.dto.response.StudentSubjectResponse;
import com.ume.studentsystem.service.StudentSubjectService;
import com.ume.studentsystem.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student-subjects")
@RequiredArgsConstructor
public class StudentSubjectController {

    private final StudentSubjectService studentSubjectService;

    @PostMapping("/assign")
    public ResponseEntity<APIResponse<List<StudentSubjectResponse>>> assignSubjects(@Valid @RequestBody AssignStudentSubjectRequest request) {
        return ResponseEntity.ok(APIResponse.ok(studentSubjectService.assignSubjects(request)));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<APIResponse<List<StudentSubjectResponse>>> getByStudent(@PathVariable Long studentId){
        return ResponseEntity.ok(APIResponse.ok(studentSubjectService.getSubjectsByStudent(studentId)));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<APIResponse<List<StudentSubjectResponse>>> getBySubject(@PathVariable Long subjectId){
        return ResponseEntity.ok(APIResponse.ok(studentSubjectService.getStudentBySubject(subjectId)));
    }

}
