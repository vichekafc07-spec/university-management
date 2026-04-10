package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.AssignLecturerRequest;
import com.ume.studentsystem.dto.response.LecturerAssignmentResponse;
import com.ume.studentsystem.service.LecturerAssignmentService;
import com.ume.studentsystem.util.APIResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lecturer-assignments")
@RequiredArgsConstructor
public class LecturerController {

    private final LecturerAssignmentService lecturerService;

    @PostMapping("/assign")
    public ResponseEntity<APIResponse<LecturerAssignmentResponse>> assign(@Valid @RequestBody AssignLecturerRequest request) {
        return ResponseEntity.ok(APIResponse.create(lecturerService.assignLecturer(request)));
    }

    @GetMapping("/lecturer/{lecturerId}")
    public ResponseEntity<APIResponse<PageResponse<LecturerAssignmentResponse>>> getByLecturer(@PathVariable Long lecturerId,
                                                                                               @RequestParam(required = false) String lecturerName,
                                                                                               @RequestParam(required = false) String lecturerCode,
                                                                                               @RequestParam(required = false) String sortBy,
                                                                                               @RequestParam(required = false) String sortAs,
                                                                                               @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                                               @RequestParam(required = false, defaultValue = "5") Integer size
                                                                                               ) {
        PageResponse<LecturerAssignmentResponse> pageResponse = lecturerService.getLecturerAssignments(lecturerId,lecturerName,lecturerCode,sortBy,sortAs,page,size);
        return ResponseEntity.ok(APIResponse.ok(pageResponse));
    }

    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<APIResponse<List<LecturerAssignmentResponse>>> getByClassroom(@PathVariable Long classroomId) {
        return ResponseEntity.ok(APIResponse.ok(lecturerService.getClassroomAssignments(classroomId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<?>> remove(@PathVariable Long id) {
        lecturerService.removeAssignment(id);
        return ResponseEntity.noContent().build();
    }
}
