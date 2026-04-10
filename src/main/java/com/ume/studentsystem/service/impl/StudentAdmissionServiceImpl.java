package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.response.AdmissionResponse;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.model.enums.StudentStatus;
import com.ume.studentsystem.repository.StudentRepository;
import com.ume.studentsystem.service.StudentAdmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentAdmissionServiceImpl implements StudentAdmissionService {

    private final StudentRepository studentRepository;

    @Override
    public AdmissionResponse approve(Long studentId) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));

        student.setStatus(StudentStatus.ACTIVE);
        studentRepository.save(student);

        return new AdmissionResponse(
                student.getId(),
                student.getFullName(),
                student.getStudentCode(),
                student.getStatus()
        );
    }

    @Override
    public AdmissionResponse reject(Long studentId) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));

        student.setStatus(StudentStatus.REJECTED);
        studentRepository.save(student);

        return new AdmissionResponse(
                student.getId(),
                student.getFullName(),
                student.getStudentCode(),
                student.getStatus()
        );
    }
}