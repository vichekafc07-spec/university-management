package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.RoomRequest;
import com.ume.studentsystem.dto.response.RoomResponse;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.RoomMapper;
import com.ume.studentsystem.model.Room;
import com.ume.studentsystem.repository.RoomRepository;
import com.ume.studentsystem.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomResponse create(RoomRequest request) {

        if (roomRepository.existsByName(request.name())) {
            throw new BadRequestException("Room already exists");
        }

        var room = roomMapper.toEntity(request);
        roomRepository.save(room);

        return roomMapper.toResponse(room);
    }

    public List<RoomResponse> getAll() {
        return roomRepository.findAll()
                .stream()
                .map(roomMapper::toResponse)
                .toList();
    }

    public RoomResponse getById(Integer id) {
        var room = getRoomById(id);
        return roomMapper.toResponse(room);
    }

    public RoomResponse update(Integer id, RoomRequest request) {

        var room = getRoomById(id);
        roomMapper.updateRoom(request,room);
        roomRepository.save(room);

        return roomMapper.toResponse(room);
    }

    public void delete(Integer id) {
        var room = getRoomById(id);
        roomRepository.delete(room);
    }

    @Override
    public RoomResponse restore(Long id) {
        var room = roomRepository.findByIdIncludingDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));
        room.setDeleted(false);
        room.setDeletedAt(null);
        var saved = roomRepository.save(room);
        return roomMapper.toResponse(saved);
    }

    private Room getRoomById(Integer id){
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));
    }
}
