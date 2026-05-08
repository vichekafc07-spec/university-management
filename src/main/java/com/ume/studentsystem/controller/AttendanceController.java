package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.AttendanceRequest;
import com.ume.studentsystem.dto.request.UpdateAttendanceStatus;
import com.ume.studentsystem.dto.response.AttendanceResponse;
import com.ume.studentsystem.service.AttendanceService;
import com.ume.studentsystem.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    @PreAuthorize("hasAuthority('staff:write')")
    public ResponseEntity<APIResponse<List<AttendanceResponse>>> mark(@Valid @RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(APIResponse.ok(attendanceService.markAttendance(request)));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAuthority('staff:read')")
    public ResponseEntity<APIResponse<List<AttendanceResponse>>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(APIResponse.ok(attendanceService.getByStudent(studentId)));
    }

    @GetMapping("/subject/{subjectId}")
    @PreAuthorize("hasAuthority('staff:read')")
    public ResponseEntity<APIResponse<List<AttendanceResponse>>> getBySubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(APIResponse.ok(attendanceService.getBySubject(subjectId)));
    }

    @PatchMapping("/status/{id}")
    @PreAuthorize("hasAuthority('staff:write')")
    public ResponseEntity<APIResponse<AttendanceResponse>> updateStatus(@PathVariable Long id,
                                                                        @RequestBody UpdateAttendanceStatus status){
        return ResponseEntity.ok(APIResponse.ok(attendanceService.updateAttendanceStatus(id,status)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('staff:write')")
    public ResponseEntity<APIResponse<?>> delete(@PathVariable Long id) {
        attendanceService.delete(id);
        return ResponseEntity.ok(APIResponse.ok("Deleted successfully"));
    }
}
