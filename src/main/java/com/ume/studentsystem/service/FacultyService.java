package com.ume.studentsystem.service;

import com.ume.studentsystem.dto.response.academic.FacultyResponse;
import com.ume.studentsystem.model.Faculty;

import java.util.List;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);
    List<FacultyResponse> getAllFaculty();
    Faculty updateFaculty(Byte id, Faculty faculty);
    void deleteFaculty(Byte id);

    FacultyResponse restoreFaculty(Byte id);
}
