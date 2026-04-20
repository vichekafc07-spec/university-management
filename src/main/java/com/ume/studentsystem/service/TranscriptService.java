package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.response.TranscriptResponse;

public interface TranscriptService {

    TranscriptResponse getTranscript(Long studentId);
}
