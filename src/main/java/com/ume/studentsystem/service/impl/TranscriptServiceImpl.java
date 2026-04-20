package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.response.TranscriptItemResponse;
import com.ume.studentsystem.dto.response.TranscriptResponse;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.model.enums.GradeStatus;
import com.ume.studentsystem.repository.GradeRepository;
import com.ume.studentsystem.service.TranscriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TranscriptServiceImpl implements TranscriptService {

    private final GradeRepository gradeRepository;

    @Override
    public TranscriptResponse getTranscript(Long studentId) {

        var grades = gradeRepository
                .findByStudentSubject_StudentClassroom_Student_Id(studentId);

        if (grades.isEmpty()) {
            throw new ResourceNotFoundException("No transcript found");
        }

        var first = grades.get(0);
        var student = first.getStudentSubject()
                .getStudentClassroom()
                .getStudent();

        List<TranscriptItemResponse> items = new ArrayList<>();

        int totalCredits = 0;
        double totalPoints = 0;

        int passed = 0;
        int failed = 0;

        for (var g : grades) {

            var subject = g.getStudentSubject().getSubject();
            int credit = subject.getCredit();

            items.add(
                    new TranscriptItemResponse(
                            subject.getCode(),
                            subject.getTitle(),
                            credit,
                            g.getTotalScore(),
                            g.getGrade(),
                            g.getGpa()
                    )
            );

            totalCredits += credit;
            totalPoints += (credit * g.getGpa());

            if (g.getGrade() == GradeStatus.F) {
                failed++;
            } else {
                passed++;
            }
        }

        double cgpa = totalCredits == 0 ? 0 : totalPoints / totalCredits;

        return new TranscriptResponse(
                student.getId(),
                student.getFullName(),
                student.getStudentCode(),

                items,

                totalCredits,
                round(cgpa),
                round(cgpa), // same for now
                passed,
                failed
        );
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
