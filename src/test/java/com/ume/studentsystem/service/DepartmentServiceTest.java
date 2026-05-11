package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.request.DepartmentRequest;
import com.ume.studentsystem.dto.response.DepartmentResponse;
import com.ume.studentsystem.exceptions.DuplicateResourceException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.DepartmentMapper;
import com.ume.studentsystem.model.Department;
import com.ume.studentsystem.model.Faculty;
import com.ume.studentsystem.repository.DepartmentRepository;
import com.ume.studentsystem.repository.FacultyRepository;
import com.ume.studentsystem.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @Test
    void addDepartmentTest(){

        // given
        var request = new DepartmentRequest("IT", (byte) 1);

        var faculty = new Faculty();
        faculty.setId((byte) 1);
        faculty.setName("CS");

        var entity = new Department();
        entity.setName("IT");

        var saved = new Department();
        saved.setName("IT");
        saved.setFaculty(faculty);

        var response = new DepartmentResponse(1,"IT","CS");

        // then
        when(departmentRepository.findByNameIgnoreCase("IT"))
                .thenReturn(Optional.empty());

        when(facultyRepository.findById((byte) 1))
                .thenReturn(Optional.of(faculty));

        when(departmentMapper.toEntity(request))
                .thenReturn(entity);

        when(departmentRepository.save(any()))
                .thenReturn(saved);

        when(departmentMapper.toResponse(saved))
                .thenReturn(response);

        var result = departmentService.addDepartment(request);

        // when
        assertEquals("IT", result.name());
    }

    @Test
    void shouldThrowDuplicateTest(){

        // given
        var request = new DepartmentRequest("IT", (byte) 1);

        // when
        when(departmentRepository.findByNameIgnoreCase("IT"))
                .thenReturn(Optional.of(new Department()));

        // then
        assertThrows(DuplicateResourceException.class,
                () -> departmentService.addDepartment(request));
    }

    @Test
    void shouldThrowNotFoundTest(){

        // given
        var request = new DepartmentRequest("Law", (byte) 1);

        // when
        when(departmentRepository.findByNameIgnoreCase("Law"))
                .thenReturn(Optional.empty());

        when(facultyRepository.findById((byte) 1))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class,
                () -> departmentService.addDepartment(request));
    }

    @Test
    void getByFacultyTest(){

        // when
        when(facultyRepository.existsById((byte) 1))
                .thenReturn(true);

        when(departmentRepository.findByFaculty_Id((byte) 1))
                .thenReturn(List.of(new Department()));

        when(departmentMapper.toResponse(any()))
                .thenReturn(new DepartmentResponse(1,"IT","CS"));

        List<DepartmentResponse> result = departmentService.getDepartmentByFaculty((byte) 1);

        // then
        assertEquals(1, result.size());

    }

    @Test
    void shouldThrowWhenFacultyNotFoundForGet() {

        // when
        when(facultyRepository.existsById((byte) 1))
                .thenReturn(false);

        // then
        assertThrows(ResourceNotFoundException.class,
                () -> departmentService.getDepartmentByFaculty((byte) 1));
    }

    @Test
    void updateFacultyTest(){

        // given
        var request = new DepartmentRequest("IT",(byte) 1);

        var existing = new Department();
        existing.setId(1);

        var faculty = new Faculty();
        faculty.setId((byte) 1);

        // when
        when(departmentRepository.findById(1))
                .thenReturn(Optional.of(existing));

        when(facultyRepository.findById((byte) 1))
                .thenReturn(Optional.of(faculty));

        when(departmentRepository.save(any()))
                .thenReturn(existing);

        when(departmentMapper.toResponse(existing))
                .thenReturn(new DepartmentResponse(1,"ICT","CS"));

        var response = departmentService.updateDepartment(1,request);

        assertEquals("ICT",response.name());
    }

    @Test
    void shouldThrowWhenUpdateNotFound() {
        when(departmentRepository.findById(1))
                .thenReturn(Optional.empty());

        DepartmentRequest request = new DepartmentRequest("New", (byte) 1);

        assertThrows(ResourceNotFoundException.class,
                () -> departmentService.updateDepartment(1, request));
    }

    @Test
    void deleteDepartmentTest(){

        // given
        var department = new Department();
        department.setId(1);

        // when
        when(departmentRepository.findById(1))
                .thenReturn(Optional.of(department));

        departmentService.deleteDepartment(1);

        // then
        verify(departmentRepository).delete(department);
    }

}
