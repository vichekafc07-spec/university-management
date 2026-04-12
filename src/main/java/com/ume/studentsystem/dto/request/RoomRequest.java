package com.ume.studentsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoomRequest(

        @NotBlank(message = "Room name is required")
        String name,

        @NotNull(message = "Capacity is required")
        Integer capacity,

        @NotBlank(message = "Building is required")
        String building
) {}
