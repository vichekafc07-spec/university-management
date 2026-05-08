package com.ume.studentsystem.controller.academic;

import com.ume.studentsystem.dto.request.RoomRequest;
import com.ume.studentsystem.dto.response.RoomResponse;
import com.ume.studentsystem.service.RoomService;
import com.ume.studentsystem.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<RoomResponse>> create(@Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(APIResponse.ok(roomService.create(request)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<APIResponse<List<RoomResponse>>> getAll() {
        return ResponseEntity.ok(APIResponse.ok(roomService.getAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<APIResponse<RoomResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(APIResponse.ok(roomService.getById(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<RoomResponse>> update(@PathVariable Integer id,
                                                            @Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(APIResponse.ok(roomService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/restore/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<APIResponse<RoomResponse>> restore(@PathVariable Long id){
        return ResponseEntity.ok(APIResponse.ok(roomService.restore(id)));
    }

}
