package com.ume.studentsystem.dto.response.academic;

public record RoomResponse(
        Long id,
        String name,
        Integer capacity,
        String building
) {}
