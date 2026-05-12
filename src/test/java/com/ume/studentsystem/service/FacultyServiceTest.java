package com.ume.studentsystem.service;

import com.ume.studentsystem.exceptions.DuplicateResourceException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.model.Faculty;
import com.ume.studentsystem.repository.FacultyRepository;
import com.ume.studentsystem.service.impl.academic.FacultyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;
    private FacultyService facultyService;

    @BeforeEach
    void setUp() {
        facultyService = new FacultyServiceImpl(facultyRepository);
    }

    @Test
    void getAllFacultyTest(){
        // when
        facultyService.getAllFaculty();
        // then
        facultyRepository.findAll();
    }

    @Test
    void addFacultyTest(){

        // given
        var faculty = new Faculty();
        faculty.setName("Science and Technology");

        // then
        when(facultyRepository.findByNameIgnoreCase("Science and Technology"))
                .thenReturn(Optional.empty());

        when(facultyRepository.save(any(Faculty.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var result = facultyService.addFaculty(faculty);
        assertEquals("Science and Technology", result.getName());
        verify(facultyRepository).save(any(Faculty.class));
    }

    @Test
    void shouldDuplicateExceptionTest(){
        // given
        var faculty = new Faculty();
        faculty.setName("Software");

        // when
        when(facultyRepository.findByNameIgnoreCase("Software"))
                .thenReturn(Optional.of(new Faculty()));

        // then
        assertThrows(DuplicateResourceException.class,
                () -> facultyService.addFaculty(faculty));
    }

    @Test
    void updateFacultyTest(){

        // given
        var faculty = new Faculty();
        faculty.setId((byte)1);
        faculty.setName("Software");

        var update = new Faculty();
        update.setName("Engineer");

        // when
        when(facultyRepository.findById((byte) 1))
                .thenReturn(Optional.of(faculty));

        when(facultyRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var result = facultyService.updateFaculty((byte) 1 , update);

        // then
        assertEquals("Engineer", result.getName());
    }

    @Test
    void shouldNotFoundExceptionTest(){
        // when
        when(facultyRepository.findById((byte) 1))
                .thenReturn(Optional.empty());
        var update = new Faculty();
        update.setName("Engineer");

        // then
        assertThrows(ResourceNotFoundException.class,
                () -> facultyService.updateFaculty((byte) 1, update));
    }

    @Test
    void deleteFaculty(){
        // given
        var faculty = new Faculty();
        faculty.setId((byte) 1);

        // when
        when(facultyRepository.findById((byte) 1))
                .thenReturn(Optional.of(faculty));
        facultyService.deleteFaculty((byte) 1);
        // then
        verify(facultyRepository).delete(faculty);
    }

    @Test
    void shouldThrowNotFoundExceptionTest(){
        when(facultyRepository.findById((byte) 1))
                .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> facultyService.deleteFaculty((byte) 1));
    }

}
