package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.TimetableRequest;
import com.ume.studentsystem.dto.response.TimetableResponse;
import com.ume.studentsystem.service.TimetableService;
import com.ume.studentsystem.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/timetables")
@RequiredArgsConstructor
public class TimetableController {

    private final TimetableService timetableService;

    @PostMapping
    public ResponseEntity<APIResponse<TimetableResponse>> create(@Valid @RequestBody TimetableRequest request) {
        return ResponseEntity.ok(APIResponse.ok(timetableService.create(request)));
    }

    @GetMapping("/classroom/{id}")
    public ResponseEntity<APIResponse<List<TimetableResponse>>> getByClassroom(@PathVariable Long id) {
        return ResponseEntity.ok(APIResponse.ok(timetableService.getByClassroom(id)));
    }

    @GetMapping("/lecturer/{id}")
    public ResponseEntity<APIResponse<List<TimetableResponse>>> getByLecturer(@PathVariable Long id) {
        return ResponseEntity.ok(APIResponse.ok(timetableService.getByLecturer(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        timetableService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
