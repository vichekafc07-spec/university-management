package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.SubjectRequest;
import com.ume.studentsystem.dto.response.SubjectResponse;
import com.ume.studentsystem.service.SubjectService;
import com.ume.studentsystem.util.APIResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping
    @PreAuthorize("hasAuthority('staff:read')")
    public ResponseEntity<APIResponse<PageResponse<SubjectResponse>>> getAll(@RequestParam(required = false) Long id,
                                                                           @RequestParam(required = false) String code,
                                                                           @RequestParam(required = false) String title,
                                                                           @RequestParam(required = false) String department,
                                                                           @RequestParam(required = false) String sortBy,
                                                                           @RequestParam(required = false) String sortAs,
                                                                           @RequestParam(required = false , defaultValue = "1") Integer page,
                                                                           @RequestParam(required = false , defaultValue = "5") Integer size) {
        PageResponse<SubjectResponse> pageResponse = subjectService.getAllStaff(id,code,title,department,sortBy,sortAs,page,size);
        return ResponseEntity.ok(APIResponse.ok(pageResponse));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('staff:write')")
    public ResponseEntity<APIResponse<SubjectResponse>> create(@Valid @RequestBody SubjectRequest request) {
        return ResponseEntity.ok(APIResponse.ok(subjectService.create(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('staff:write')")
    public ResponseEntity<APIResponse<SubjectResponse>> update(@PathVariable Long id,@Valid @RequestBody SubjectRequest request) {
        return ResponseEntity.ok(APIResponse.ok(subjectService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('staff:write')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
