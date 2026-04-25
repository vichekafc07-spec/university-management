package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface FacultyRepository extends JpaRepository<Faculty,Byte> {
    @Query("""
    SELECT f.name
    FROM Student s
    JOIN s.faculty f
    GROUP BY f.name
    ORDER BY COUNT(s.id) DESC
    LIMIT 1
    """)
    String getTopFaculty();
}
