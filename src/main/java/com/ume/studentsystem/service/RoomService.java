package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.RoomRequest;
import com.ume.studentsystem.dto.response.RoomResponse;

import java.util.List;

public interface RoomService {
    RoomResponse create(RoomRequest request);
    List<RoomResponse> getAll();
    RoomResponse getById(Long id);
    RoomResponse update(Long id,RoomRequest request);
    void delete(Long id);
    RoomResponse restore(Long id);
}
