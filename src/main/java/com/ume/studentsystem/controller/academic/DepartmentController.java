package com.ume.studentsystem.controller.academic;

import com.ume.studentsystem.dto.request.DepartmentRequest;
import com.ume.studentsystem.dto.response.academic.DepartmentResponse;
import com.ume.studentsystem.service.DepartmentService;
import com.ume.studentsystem.util.APIResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    @PreAuthorize("hasAuthority('dean:read')")
    public ResponseEntity<APIResponse<PageResponse<DepartmentResponse>>> getAll(@RequestParam(required = false) Integer id,
                                                                                @RequestParam(required = false) String name,
                                                                                @RequestParam(required = false) String sortBy,
                                                                                @RequestParam(required = false) String sortAs,
                                                                                @RequestParam(required = false , defaultValue = "1") Integer page,
                                                                                @RequestParam(required = false , defaultValue = "5") Integer size){
        return ResponseEntity.ok(APIResponse.ok(departmentService.getAllDepartment(id, name, sortBy, sortAs, page, size)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('dean:write')")
    public ResponseEntity<APIResponse<DepartmentResponse>> add(@Valid @RequestBody DepartmentRequest request){
        return ResponseEntity.ok(APIResponse.create(departmentService.addDepartment(request)));
    }

    @GetMapping("/{facultyId}/faculty")
    @PreAuthorize("hasAuthority('dean:read')")
    public ResponseEntity<APIResponse<List<DepartmentResponse>>> getDepartmentByFaculty(@PathVariable Byte facultyId){
        return ResponseEntity.ok(APIResponse.ok(departmentService.getDepartmentByFaculty(facultyId)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('dean:write')")
    public ResponseEntity<APIResponse<DepartmentResponse>> update(@PathVariable Integer id,
                                                                  @Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.ok(APIResponse.ok(departmentService.updateDepartment(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('dean:write')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/restore/{id}")
    @PreAuthorize("hasAuthority('dean:write')")
    public ResponseEntity<?> restoreDepartment(@PathVariable Integer id){
        return ResponseEntity.ok(departmentService.restoreDept(id));
    }

}
