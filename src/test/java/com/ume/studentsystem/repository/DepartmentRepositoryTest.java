package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Department;
import com.ume.studentsystem.model.Faculty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Test
    void shouldSaveDepartment(){
        // given
        var faculty = new Faculty();
        faculty.setName("Science and Technology");
        facultyRepository.save(faculty);

        var dept = new Department();
        dept.setName("Network Engineer");
        dept.setFaculty(faculty);
        var saved = departmentRepository.save(dept);

        // then
        assertNotNull(saved.getId());
        assertEquals("Network Engineer",saved.getName());
        assertEquals("Science and Technology",saved.getFaculty().getName());

    }

    @Test
    void findByNameTest(){
        // given
        var faculty = new Faculty(null,"It");
        facultyRepository.save(faculty);

        var dept = new Department(null,"CS",faculty);
        departmentRepository.save(dept);

        // when
        Optional<Department> result = departmentRepository.findByNameIgnoreCase("cs");

        // then
        assertTrue(result.isPresent());
        assertEquals("CS",dept.getName());

    }

    @Test
    void findByFacultyIdTest(){
        // given
        var faculty = new Faculty();
        faculty.setName("English literature");
        facultyRepository.save(faculty);

        var dept = new Department();
        dept.setName("English Grammar");
        dept.setFaculty(faculty);
        departmentRepository.save(dept);

        var dept1 = new Department();
        dept1.setName("English Communicate");
        dept1.setFaculty(faculty);
        departmentRepository.save(dept1);

        // then
        List<Department> result = departmentRepository.findByFaculty_Id(faculty.getId());

        // when
        assertThat(result).hasSize(2);
    }



}
