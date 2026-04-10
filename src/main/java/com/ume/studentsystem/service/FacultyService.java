package com.ume.studentsystem.service;

import com.ume.studentsystem.model.Faculty;

import java.util.List;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);
    List<Faculty> getAllFaculty();
    Faculty updateFaculty(Byte id, Faculty faculty);
    void deleteFaculty(Byte id);
}
