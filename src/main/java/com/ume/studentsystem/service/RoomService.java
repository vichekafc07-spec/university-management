package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.RoomRequest;
import com.ume.studentsystem.dto.response.RoomResponse;
import com.ume.studentsystem.util.PageResponse;

public interface RoomService {
    RoomResponse create(RoomRequest request);
    RoomResponse getById(Integer id);
    RoomResponse update(Integer id,RoomRequest request);
    void delete(Integer id);
    RoomResponse restore(Long id);

    PageResponse<RoomResponse> getAllRoom(Integer id, String name, String building, String sortBy, String sortAs, Integer page, Integer size);
}
