package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.request.SessionRequest;
import com.ume.studentsystem.dto.response.SessionResponse;
import com.ume.studentsystem.service.SessionService;
import com.ume.studentsystem.util.APIResponse;
import com.ume.studentsystem.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<APIResponse<SessionResponse>> create(@Valid @RequestBody SessionRequest request) {
        return ResponseEntity.ok(APIResponse.ok(sessionService.create(request)));
    }

    @GetMapping
    public ResponseEntity<APIResponse<PageResponse<SessionResponse>>> getAll(@RequestParam(required = false) String subjectName,
                                                                             @RequestParam(required = false) String roomName,
                                                                             @RequestParam(required = false) String day,
                                                                             @RequestParam(required = false) String sortBy,
                                                                             @RequestParam(required = false) String sortAs,
                                                                             @RequestParam(required = false,defaultValue = "1") Integer page,
                                                                             @RequestParam(required = false,defaultValue = "5") Integer size
                                                                     ) {
        return ResponseEntity.ok(APIResponse.ok(sessionService.getAll(subjectName,roomName,day,sortBy,sortAs,page,size)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<SessionResponse>> update(@PathVariable Long id,
                                                               @Valid @RequestBody SessionRequest request){
        return ResponseEntity.ok(APIResponse.ok(sessionService.update(id,request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<?>> delete(@PathVariable Long id) {
        sessionService.delete(id);
        return ResponseEntity.ok(APIResponse.ok("Deleted successfully"));
    }
}
