package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.RoomRequest;
import com.ume.studentsystem.dto.response.RoomResponse;

import java.util.List;

public interface RoomService {
    RoomResponse create(RoomRequest request);
    List<RoomResponse> getAll();
    RoomResponse getById(Integer id);
    RoomResponse update(Integer id,RoomRequest request);
    void delete(Integer id);
    RoomResponse restore(Long id);
}
