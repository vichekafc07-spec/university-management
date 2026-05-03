package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void shouldSaveDepartment(){
        var dept = new Department();
        dept.setName("Software Engineer");
    }

}
