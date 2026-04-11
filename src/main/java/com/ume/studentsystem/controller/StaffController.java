package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.StaffRequest;
import com.ume.studentsystem.dto.request.StaffUpdateRequest;
import com.ume.studentsystem.dto.response.StaffResponse;
import com.ume.studentsystem.service.StaffService;
import com.ume.studentsystem.util.APIResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staffs")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<StaffResponse>> addStaff(@Valid @RequestBody StaffRequest request) {
        return ResponseEntity.ok(APIResponse.create(staffService.addStaff(request)));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<StaffResponse>> updateStaff(@Valid @RequestBody StaffUpdateRequest request, @PathVariable Long id){
        return ResponseEntity.ok(APIResponse.ok(staffService.updateStaff(request,id)));
    }

    @PatchMapping("/activate/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<String>> activate(@PathVariable Long id){
        return ResponseEntity.ok(APIResponse.ok(staffService.activate(id)));
    }

    @PatchMapping("/deactivate/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<String>> deactivate(@PathVariable Long id){
        return ResponseEntity.ok(APIResponse.ok(staffService.deactivate(id)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<APIResponse<PageResponse<StaffResponse>>> getAll(@RequestParam(required = false) Long id,
                                                              @RequestParam(required = false) String fullName,
                                                              @RequestParam(required = false) String position,
                                                              @RequestParam(required = false) String faculty,
                                                              @RequestParam(required = false) Boolean active,
                                                              @RequestParam(required = false) String sortBy,
                                                              @RequestParam(required = false) String sortAs,
                                                              @RequestParam(required = false , defaultValue = "1") Integer page,
                                                              @RequestParam(required = false , defaultValue = "5") Integer size) {
        PageResponse<StaffResponse> pageResponse = staffService.getAllStaff(id,fullName,position,faculty,active,sortBy,sortAs,page,size);
        return ResponseEntity.ok(APIResponse.ok(pageResponse));
    }

    @GetMapping("/department/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<APIResponse<List<StaffResponse>>> getByDepartment(@PathVariable Integer id) {
        return ResponseEntity.ok(APIResponse.ok(staffService.getByDepartment(id)));
    }

    @GetMapping("/faculty/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<APIResponse<List<StaffResponse>>> getByFaculty(@PathVariable Byte id) {
        return ResponseEntity.ok(APIResponse.ok(staffService.getByFaculty(id)));
    }

    @GetMapping("/lecturers")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<APIResponse<List<StaffResponse>>> getLecturers() {
        return ResponseEntity.ok(APIResponse.ok(staffService.getLecturers()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        staffService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/restore/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<?>> restoreStaff(@PathVariable Long id) {
        return ResponseEntity.ok(APIResponse.ok(staffService.restore(id)));
    }
}
