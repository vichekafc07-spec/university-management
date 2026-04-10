package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.AssignStudentClassroomRequest;
import com.ume.studentsystem.dto.request.RemoveStudentClassroomRequest;
import com.ume.studentsystem.dto.response.StudentClassroomResponse;
import com.ume.studentsystem.service.StudentClassroomService;
import com.ume.studentsystem.util.APIResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student-classrooms")
@RequiredArgsConstructor
public class StudentClassController {

    private final StudentClassroomService scService;

    @PostMapping("/assign")
    public ResponseEntity<APIResponse<List<StudentClassroomResponse>>> assign(@Valid @RequestBody AssignStudentClassroomRequest request) {
        return ResponseEntity.ok(APIResponse.create(scService.assignStudent(request)));
    }

    @DeleteMapping("/unassign")
    public ResponseEntity<?> remove(@Valid @RequestBody RemoveStudentClassroomRequest request) {
        scService.removeStudent(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<APIResponse<PageResponse<StudentClassroomResponse>>> getStudents(@PathVariable Long classroomId,
                                                                                           @RequestParam(required = false) String studentCode,
                                                                                           @RequestParam(required = false) String sortBy,
                                                                                           @RequestParam(required = false) String sortAs,
                                                                                           @RequestParam(required = false,defaultValue = "1") Integer page,
                                                                                           @RequestParam(required = false,defaultValue = "5") Integer size
                                                                                           ) {
        PageResponse<StudentClassroomResponse> pageResponse = scService.getStudentsInClassroom(classroomId,studentCode,sortBy,sortAs,page,size);

        return ResponseEntity.ok(APIResponse.ok(pageResponse));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<APIResponse<List<StudentClassroomResponse>>> getHistory(@PathVariable Long studentId) {
        return ResponseEntity.ok(APIResponse.ok(scService.getStudentHistory(studentId)));
    }

    @PostMapping("/promote/{classroomId}/to/{nextClassroomId}")
    public ResponseEntity<?> promote(@PathVariable Long classroomId,
                                     @PathVariable Long nextClassroomId) {
        scService.promoteClassroom(classroomId, nextClassroomId);
        return ResponseEntity.ok("Classroom promoted successfully");
    }
}
