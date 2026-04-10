package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.model.Faculty;
import com.ume.studentsystem.repository.FacultyRepository;
import com.ume.studentsystem.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    @Override
    public Faculty addFaculty(Faculty faculty) {
        var fac = new Faculty();
        fac.setId(faculty.getId());
        fac.setName(faculty.getName());
        return facultyRepository.save(fac);
    }

    @Override
    public Faculty updateFaculty(Byte id, Faculty faculty) {
        Faculty existing = facultyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id: " + id));
        existing.setName(faculty.getName());
        return facultyRepository.save(existing);
    }

    @Override
    public void deleteFaculty(Byte id) {
        Faculty existing = facultyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id: " + id));
        facultyRepository.delete(existing);
    }

    @Override
    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }


}
