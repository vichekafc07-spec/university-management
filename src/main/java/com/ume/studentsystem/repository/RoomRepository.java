package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByName(String name);
    @Query(value = "SELECT * FROM rooms WHERE id = :id", nativeQuery = true)
    Optional<Room> findByIdIncludingDeleted(@Param("id") Long id);
}
