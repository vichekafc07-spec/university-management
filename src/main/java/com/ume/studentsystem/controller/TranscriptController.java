package com.ume.studentsystem.controller;

import com.ume.studentsystem.dto.response.TranscriptResponse;
import com.ume.studentsystem.service.TranscriptService;
import com.ume.studentsystem.service.impl.TranscriptPdfService;
import com.ume.studentsystem.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/v1/transcripts")
@RequiredArgsConstructor
public class TranscriptController {

    private final TranscriptService transcriptService;
    private final TranscriptPdfService transcriptPdfService;

    @GetMapping("/{studentId}")
    public ResponseEntity<APIResponse<TranscriptResponse>> getTranscript(@PathVariable Long studentId) {
        return ResponseEntity.ok(APIResponse.ok(transcriptService.getTranscript(studentId)));
    }

    @GetMapping("/{studentId}/pdf")
    public ResponseEntity<InputStreamResource> pdf(@PathVariable Long studentId){
        ByteArrayInputStream in = transcriptPdfService.export(studentId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","inline; filename=transcript.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(in));
    }

}
