package com.ume.studentsystem.controller;

import com.ume.studentsystem.model.Faculty;
import com.ume.studentsystem.service.FacultyService;
import com.ume.studentsystem.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/faculties")
@RequiredArgsConstructor
public class FacultyController {
    private final FacultyService facultyService;

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<Faculty>> add(@Valid @RequestBody Faculty faculty){
        return ResponseEntity.ok(APIResponse.create(facultyService.addFaculty(faculty)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('dean:read')")
    public ResponseEntity<APIResponse<List<Faculty>>> getAll(){
        return ResponseEntity.ok(APIResponse.ok(facultyService.getAllFaculty()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<Faculty>> update(@PathVariable Byte id,
                                                       @Valid @RequestBody Faculty faculty) {

        return ResponseEntity.ok(APIResponse.ok(facultyService.updateFaculty(id, faculty)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> delete(@PathVariable Byte id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.notFound().build();
    }

}
