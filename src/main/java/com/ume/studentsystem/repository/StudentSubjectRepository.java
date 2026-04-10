package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.StudentSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Long>, JpaSpecificationExecutor<StudentSubject> {

    List<StudentSubject> findByStudentClassroom_Student_IdInAndSubjectIdIn(Set<Long> studentClassroomIds, Set<Long> subjectIds);

    List<StudentSubject> findByStudentClassroom_Student_Id(Long studentClassroomId);

    List<StudentSubject> findBySubject_Id(Long subjectId);
}
