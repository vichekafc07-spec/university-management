package com.ume.studentsystem.dto.response;

public record RoomResponse(
        Long id,
        String name,
        Integer capacity,
        String building
) {}
