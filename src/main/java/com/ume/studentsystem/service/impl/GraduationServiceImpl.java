package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.response.GraduationResponse;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.model.enums.GradeStatus;
import com.ume.studentsystem.repository.GradeRepository;
import com.ume.studentsystem.service.GraduationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GraduationServiceImpl implements GraduationService {

    private final GradeRepository gradeRepository;

    private static final int REQUIRED_CREDITS = 120;
    private static final double MIN_CGPA = 2.0;

    @Override
    public GraduationResponse checkEligibility(Long studentId) {

        var grades = gradeRepository
                .findByStudentSubject_StudentClassroom_Student_Id(studentId);

        if (grades.isEmpty()) {
            throw new ResourceNotFoundException("No academic record found");
        }

        var student = grades.getFirst()
                .getStudentSubject()
                .getStudentClassroom()
                .getStudent();

        int totalCredits = 0;
        double totalPoints = 0;
        int failedSubjects = 0;

        for (var g : grades) {

            int credit = g.getStudentSubject()
                    .getSubject()
                    .getCredit();

            totalCredits += credit;
            totalPoints += credit * g.getGpa();

            if (g.getGrade() == GradeStatus.F) {
                failedSubjects++;
            }
        }

        double cgpa = totalCredits == 0 ? 0 : totalPoints / totalCredits;

        List<String> reasons = new ArrayList<>();

        if (totalCredits < REQUIRED_CREDITS) {
            reasons.add("Not enough credits");
        }

        if (cgpa < MIN_CGPA) {
            reasons.add("CGPA below minimum");
        }

        if (failedSubjects > 0) {
            reasons.add("Has failed subjects");
        }

        boolean eligible = reasons.isEmpty();

        return new GraduationResponse(
                student.getId(),
                student.getFullName(),
                student.getStudentCode(),
                totalCredits,
                round(cgpa),
                failedSubjects,
                eligible,
                reasons
        );
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
