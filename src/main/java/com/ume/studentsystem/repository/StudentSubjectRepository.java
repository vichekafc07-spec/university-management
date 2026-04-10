package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.StudentSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Long> {

    List<StudentSubject> findByStudentClassroom_Student_IdInAndSubjectIdIn(Set<Long> studentClassroomIds, Set<Long> subjectIds);

    List<StudentSubject> findByStudentClassroomStudent_Id(Long studentClassroomId);

    List<StudentSubject> findBySubject_Id(Long subjectId);
}
