package com.ume.studentsystem.service.impl.academic;

import com.ume.studentsystem.dto.request.RoomRequest;
import com.ume.studentsystem.dto.response.RoomResponse;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.RoomMapper;
import com.ume.studentsystem.model.Room;
import com.ume.studentsystem.repository.RoomRepository;
import com.ume.studentsystem.service.RoomService;
import com.ume.studentsystem.spec.SpecificationBuilder;
import com.ume.studentsystem.util.PageResponse;
import com.ume.studentsystem.util.SortResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public RoomResponse create(RoomRequest request) {

        if (roomRepository.existsByName(request.name())) {
            throw new BadRequestException("Room already exists");
        }

        var room = roomMapper.toEntity(request);
        roomRepository.save(room);

        return roomMapper.toResponse(room);
    }

    @Override
    public RoomResponse getById(Integer id) {
        var room = getRoomById(id);
        return roomMapper.toResponse(room);
    }

    @Override
    public RoomResponse update(Integer id, RoomRequest request) {

        var room = getRoomById(id);
        roomMapper.updateRoom(request,room);
        roomRepository.save(room);

        return roomMapper.toResponse(room);
    }

    @Override
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

    @Override
    public PageResponse<RoomResponse> getAllRoom(Integer id, String name, String building, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<Room> spec = new SpecificationBuilder<Room>()
                .equal("deleted",false)
                .equal("id", id)
                .like("name",name)
                .like("building",building)
                .build();
        List<String> allowSort = List.of("id","name","building");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page-1,size,sort);
        Page<Room> roomPage = roomRepository.findAll(spec,pageable);
        return PageResponse.from(roomPage,roomMapper::toResponse);
    }

    private Room getRoomById(Integer id){
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));
    }
}
