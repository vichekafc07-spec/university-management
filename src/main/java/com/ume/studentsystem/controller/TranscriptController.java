package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.response.TranscriptResponse;
import com.ume.studentsystem.service.TranscriptService;
import com.ume.studentsystem.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transcripts")
@RequiredArgsConstructor
public class TranscriptController {

    private final TranscriptService transcriptService;

    @GetMapping("/{studentId}")
    public ResponseEntity<APIResponse<TranscriptResponse>> getTranscript(@PathVariable Long studentId) {
        return ResponseEntity.ok(APIResponse.ok(transcriptService.getTranscript(studentId)));
    }
}
