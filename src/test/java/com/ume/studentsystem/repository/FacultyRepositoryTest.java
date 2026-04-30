package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Faculty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class FacultyRepositoryTest {

    @Autowired
    private FacultyRepository facultyRepository;

    @Test
    void shouldSaveFaculty(){
        var faculty = new Faculty();
        faculty.setName("Science and Technology");

        var saved = facultyRepository.save(faculty);

        assertNotNull(saved.getId());
        assertEquals("Science and Technology", saved.getName());

    }

}
