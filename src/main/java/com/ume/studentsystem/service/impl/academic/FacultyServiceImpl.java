package com.ume.studentsystem.service.impl.academic;

import com.ume.studentsystem.exceptions.DuplicateResourceException;
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
        facultyRepository.findByNameIgnoreCase(faculty.getName()).ifPresent(e -> {
            throw new DuplicateResourceException("Faculty already exists with name: " + faculty.getName());
        });
        var fac = new Faculty();
        fac.setName(faculty.getName());
        return facultyRepository.save(fac);
    }

    @Override
    public Faculty updateFaculty(Byte id, Faculty faculty) {
        Faculty existing = getById(id);
        existing.setName(faculty.getName());
        return facultyRepository.save(existing);
    }

    @Override
    public void deleteFaculty(Byte id) {
        Faculty existing = getById(id);
        facultyRepository.delete(existing);
    }

    @Override
    public String restoreFaculty(Byte id) {
        var faculty = facultyRepository.findByIdIncludingDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id : " + id));
        faculty.setDeleted(false);
        faculty.setDeletedAt(null);
        facultyRepository.save(faculty);
        return "Faculty restored successfully";
    }

    @Override
    public List<Faculty> getAllFaculty() {
        return facultyRepository.findByDeletedFalse();
    }

    private Faculty getById(Byte id){
        return facultyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id: " + id));
    }

}
