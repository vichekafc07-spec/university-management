package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.response.student.StudentResponse;
import com.ume.studentsystem.email.EmailService;
import com.ume.studentsystem.exceptions.DuplicateResourceException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.StudentMapper;
import com.ume.studentsystem.model.Department;
import com.ume.studentsystem.model.Faculty;
import com.ume.studentsystem.model.Student;
import com.ume.studentsystem.model.enums.*;
import com.ume.studentsystem.repository.DepartmentRepository;
import com.ume.studentsystem.repository.FacultyRepository;
import com.ume.studentsystem.repository.StudentRepository;
import com.ume.studentsystem.service.impl.StudentServiceImpl;
import com.ume.studentsystem.util.StudentMockTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private EmailService emailService;

    @Test
    void createStudentTest(){

        // given
        var request = StudentMockTest.studentRequest();

        MultipartFile photo = new MockMultipartFile("photo","test.png","/image/png","image-data".getBytes());

        var faculty = new Faculty();
        faculty.setId((byte) 1);

        var department = new Department();
        department.setId(1);

        var student = new Student();
        student.setStudentCode("ST001");

        var saved = new Student();
        saved.setFullName("John Doe");
        saved.setStudentCode("ST001");
        saved.setEmail("john@gmail.com");
        saved.setFaculty(faculty);
        saved.setDepartment(department);
        saved.setGeneration(1);

        // when
        when(studentRepository.existsByStudentCode("ST001"))
                .thenReturn(false);

        when(facultyRepository.findById((byte) 1))
                .thenReturn(Optional.of(faculty));

        when(departmentRepository.findById(1))
                .thenReturn(Optional.of(department));

        when(studentMapper.toEntity(request))
                .thenReturn(student);

        when(studentRepository.save(any()))
                .thenReturn(saved);

        when(studentMapper.toResponse(saved))
                .thenReturn(StudentMockTest.studentResponse());

        StudentServiceImpl spyService = Mockito.spy(studentService);

        var result = spyService.create(request,photo);

        // then
        assertEquals("ST001", result.studentCode());
    }

    @Test
    void shouldCreateStudentWithDefaultPhoto() {

        var request = StudentMockTest.studentRequest();

        Faculty faculty = new Faculty();
        faculty.setName("Engineering");

        Department department = new Department();
        department.setName("IT");

        Student entity = new Student();
        Student saved = new Student();
        saved.setStudentCode("ST001");
        saved.setFullName("John Doe");
        saved.setEmail("john@gmail.com");
        saved.setFaculty(faculty);
        saved.setDepartment(department);
        saved.setGeneration(28);
        saved.setMajor("Software Engineering");

        saved.setPhotoUrl("default-profile.png");

        when(studentRepository.existsByStudentCode(any()))
                .thenReturn(false);

        when(facultyRepository.findById(any()))
                .thenReturn(Optional.of(faculty));

        when(departmentRepository.findById(any()))
                .thenReturn(Optional.of(department));

        when(studentMapper.toEntity(any()))
                .thenReturn(entity);

        when(studentRepository.save(any()))
                .thenReturn(saved);

        when(studentMapper.toResponse(saved))
                .thenReturn(StudentMockTest.studentResponse());

        StudentResponse result = studentService.create(request, null);

        assertEquals("ST001", result.studentCode());
    }

    @Test
    void shouldThrowDuplicateExceptionTest(){

        // given
        var request = StudentMockTest.studentRequest();

        // when
        when(facultyRepository.findById((byte) 1))
                .thenReturn(Optional.of(new Faculty()));

        when(departmentRepository.findById(any()))
                .thenReturn(Optional.of(new Department()));

        when(studentRepository.existsByStudentCode(request.studentCode()))
                .thenReturn(true);

        // then
        assertThrows(DuplicateResourceException.class,
                () -> studentService.create(request,null));
    }

    @Test
    void studentNotFoundTest() {

        // when
        when(studentRepository.findById(any()))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class,
                () -> studentService.getStudentById(1L));
    }

    @Test
    void facultyNotFoundTest(){

        // given
        var request = StudentMockTest.studentRequest();

        // when
        when(facultyRepository.findById(any()))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class,
                () -> studentService.create(request,null));
    }

    @Test
    void departmentNotFoundTest(){

        // given
        var request = StudentMockTest.studentRequest();

        // when
        when(facultyRepository.findById(any()))
                .thenReturn(Optional.of(new Faculty()));

        when(departmentRepository.findById(any()))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class,
                () -> studentService.create(request,null));
    }

    @Test
    void updateStudentTest() {

        Student existing = new Student();
        existing.setId(1L);

        var request = StudentMockTest.studentRequest();

        var faculty = new Faculty();
        faculty.setId((byte) 1);

        var department = new Department();
        department.setId(1);

        when(facultyRepository.findById((byte)1))
                .thenReturn(Optional.of(faculty));

        when(departmentRepository.findById(1))
                .thenReturn(Optional.of(department));

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(studentRepository.save(any()))
                .thenReturn(existing);

        when(studentMapper.toResponse(existing))
                .thenReturn(StudentMockTest.studentResponse());

        StudentResponse result = studentService.update(1L, request);

        assertEquals("John Doe", result.fullName());
    }

    @Test
    void updateNotFoundTest() {

        when(studentRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> studentService.update(1L, StudentMockTest.studentRequest()));
    }

    @Test
    void deleteStudent(){

        // given
        var student = new Student();

        // when
        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        studentService.delete(1L);

        // then
        verify(studentRepository).delete(student);
    }

}
