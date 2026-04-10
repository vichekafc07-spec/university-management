package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.AssignSubjectRequest;
import com.ume.studentsystem.dto.request.ClassroomRequest;
import com.ume.studentsystem.dto.response.ClassroomResponse;
import com.ume.studentsystem.service.ClassroomService;
import com.ume.studentsystem.util.APIResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/classrooms")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping
    public ResponseEntity<APIResponse<PageResponse<ClassroomResponse>>> getAll(@RequestParam(required = false) Long id,
                                                                               @RequestParam(required = false) String name,
                                                                               @RequestParam(required = false) Integer year,
                                                                               @RequestParam(required = false) Integer semester,
                                                                               @RequestParam(required = false) Integer generation,
                                                                               @RequestParam(required = false) String time,
                                                                               @RequestParam(required = false) String sortBy,
                                                                               @RequestParam(required = false) String sortAs,
                                                                               @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                               @RequestParam(required = false, defaultValue = "5") Integer size
                                                                               ){
        PageResponse<ClassroomResponse> pageResponse = classroomService.getAllClassroom(id,name,year,semester,generation,time,sortBy,sortAs,page,size);
        return ResponseEntity.ok(APIResponse.ok(pageResponse));
    }

    @PostMapping
    public ResponseEntity<APIResponse<ClassroomResponse>> create(@Valid @RequestBody ClassroomRequest request) {
        return ResponseEntity.ok(APIResponse.create(classroomService.create(request)));
    }

    @PostMapping("/{id}/subjects/add")
    public ResponseEntity<APIResponse<ClassroomResponse>> addSubjects(@PathVariable Long id,
                                                                      @Valid @RequestBody AssignSubjectRequest subjects) {
        return ResponseEntity.ok(APIResponse.create(classroomService.addSubject(id, subjects)));
    }

    @PostMapping("/{id}/subjects/remove")
    public ResponseEntity<APIResponse<ClassroomResponse>> removeSubjects(@PathVariable Long id,
                                                            @Valid @RequestBody AssignSubjectRequest request) {
        return ResponseEntity.ok(APIResponse.ok(classroomService.removeSubject(id, request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ClassroomResponse>> updateSubject(@PathVariable Long id,
                                                           @Valid @RequestBody ClassroomRequest request){
        return ResponseEntity.ok(APIResponse.ok(classroomService.updateClassroom(id,request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        classroomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<APIResponse<?>> restore(@PathVariable Long id){
        return ResponseEntity.ok(APIResponse.ok(classroomService.restore(id)));
    }

}
