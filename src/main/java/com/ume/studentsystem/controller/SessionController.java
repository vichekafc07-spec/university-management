package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.SessionRequest;
import com.ume.studentsystem.dto.response.SessionResponse;
import com.ume.studentsystem.service.SessionService;
import com.ume.studentsystem.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService service;

    @PostMapping
    public ResponseEntity<APIResponse<SessionResponse>> create(@Valid @RequestBody SessionRequest request) {
        return ResponseEntity.ok(APIResponse.ok(service.create(request)));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<SessionResponse>>> getAll() {
        return ResponseEntity.ok(APIResponse.ok(service.getAll()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(APIResponse.ok("Deleted successfully"));
    }
}
