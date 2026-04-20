package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.AcademicTermRequest;
import com.ume.studentsystem.dto.response.AcademicTermResponse;
import com.ume.studentsystem.service.AcademicService;
import com.ume.studentsystem.util.APIResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/academics")
@RequiredArgsConstructor
public class AcademicTermController {

    private final AcademicService academicService;

    @PostMapping
    public ResponseEntity<APIResponse<AcademicTermResponse>> createTerm(@Valid @RequestBody AcademicTermRequest request){
        return ResponseEntity.ok(APIResponse.create(academicService.create(request)));
    }

    @GetMapping
    public ResponseEntity<APIResponse<PageResponse<AcademicTermResponse>>> getAllTerm(@RequestParam(required = false) Long id,
                                                                                      @RequestParam(required = false) Integer year,
                                                                                      @RequestParam(required = false) Integer semester,
                                                                                      @RequestParam(required = false) LocalDate startDate,
                                                                                      @RequestParam(required = false) LocalDate endDate,
                                                                                      @RequestParam(required = false) String sortBy,
                                                                                      @RequestParam(required = false) String sortAs,
                                                                                      @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                                      @RequestParam(required = false, defaultValue = "5") Integer size){
        return ResponseEntity.ok(APIResponse.ok(academicService.getAll(id,year,semester,startDate,endDate,sortBy,sortAs,page,size)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<AcademicTermResponse>> update(@PathVariable Long id,
                                                                    @Valid @RequestBody AcademicTermRequest request){
        return ResponseEntity.ok(APIResponse.ok(academicService.updateTerm(id,request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        academicService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<APIResponse<?>> restore(@PathVariable Long id){
        return ResponseEntity.ok(APIResponse.ok(academicService.restore(id)));
    }

}
