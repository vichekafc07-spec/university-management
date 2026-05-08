package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.LecturerAssignment;
import com.ume.studentsystem.model.enums.StudyTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LecturerAssignmentRepository extends JpaRepository<LecturerAssignment, Long>, JpaSpecificationExecutor<LecturerAssignment> {

    boolean existsByLecturer_IdAndClassroom_IdAndSubject_IdAndTime(Long lecturerId, Long classroomId, Long subjectId, StudyTime time);

    List<LecturerAssignment> findByClassroomId(Long classroomId);

    boolean existsByClassroom_IdAndSubject_Id(Long classroomId, Long subjectId);
}
