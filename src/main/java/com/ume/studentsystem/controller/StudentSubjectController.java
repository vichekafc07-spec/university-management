package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.AssignStudentSubjectRequest;
import com.ume.studentsystem.dto.response.StudentSubjectResponse;
import com.ume.studentsystem.service.StudentSubjectService;
import com.ume.studentsystem.util.APIResponse;
import com.ume.studentsystem.util.PageResponse;
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
    public ResponseEntity<APIResponse<PageResponse<StudentSubjectResponse>>> getByStudent(@PathVariable Long studentId,
                                                                                          @RequestParam(required = false) String studentName,
                                                                                          @RequestParam(required = false) String studentCode,
                                                                                          @RequestParam(required = false) Integer semester,
                                                                                          @RequestParam(required = false) String sortBy,
                                                                                          @RequestParam(required = false) String sortAs,
                                                                                          @RequestParam(required = false , defaultValue = "1") Integer page,
                                                                                          @RequestParam(required = false , defaultValue = "5") Integer size
                                                                                          ){
        return ResponseEntity.ok(APIResponse.ok(studentSubjectService.getSubjectsByStudent(studentId,studentName,studentCode,semester,sortBy,sortAs,page,size)));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<APIResponse<List<StudentSubjectResponse>>> getBySubject(@PathVariable Long subjectId){
        return ResponseEntity.ok(APIResponse.ok(studentSubjectService.getStudentBySubject(subjectId)));
    }

}
