package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.GradeRequest;
import com.ume.studentsystem.dto.response.GradeResponse;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.GradeMapper;
import com.ume.studentsystem.model.Grade;
import com.ume.studentsystem.model.enums.AttendanceStatus;
import com.ume.studentsystem.model.enums.GradeStatus;
import com.ume.studentsystem.repository.AttendanceRepository;
import com.ume.studentsystem.repository.GradeRepository;
import com.ume.studentsystem.repository.StudentSubjectRepository;
import com.ume.studentsystem.service.GradeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final StudentSubjectRepository studentSubjectRepository;
    private final GradeMapper gradeMapper;
    private final AttendanceRepository attendanceRepository;

    @Override
    @Transactional
    public GradeResponse create(GradeRequest request) {

        var ss = studentSubjectRepository.findById(request.studentSubjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("StudentSubject not found"));

        Grade grades = gradeRepository.findByStudentSubject_Id(request.studentSubjectId())
                .orElse(new Grade());

        double attendanceScore = calculateAttendanceScore(request.studentSubjectId());

        if (request.assignmentScore() > 30 ||
                request.midtermScore() > 30 ||
                request.finalScore() > 40) {
            throw new BadRequestException("Invalid score range");
        }

        Double total = attendanceScore + request.assignmentScore() + request.midtermScore() + request.finalScore();

        GradeStatus gradeStatus = calculateGrade(total);
        Double gpa = calculateGPA(gradeStatus);

        grades.setStudentSubject(ss);
        grades.setAttendanceScore(attendanceScore);
        grades.setAssignmentScore(request.assignmentScore());
        grades.setMidtermScore(request.midtermScore());
        grades.setFinalScore(request.finalScore());
        grades.setTotalScore(total);
        grades.setGrade(gradeStatus);
        grades.setGpa(gpa);
        var saved = gradeRepository.save(grades);
        return gradeMapper.toResponse(saved);
    }

    public List<GradeResponse> getByStudent(Long studentId) {
        return gradeRepository.findAll()
                .stream()
                .filter(g -> g.getStudentSubject().getStudentClassroom().getStudent().getId().equals(studentId))
                .map(gradeMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id){
        if (!gradeRepository.existsById(id)){
            throw new ResourceNotFoundException("Grade not found");
        }
        gradeRepository.deleteById(id);
    }

    private double calculateAttendanceScore(Long studentSubjectId){
        long absent = attendanceRepository.countByStudentSubject_IdAndStatus(studentSubjectId, AttendanceStatus.ABSENT);
        long permission = attendanceRepository.countByStudentSubject_IdAndStatus(studentSubjectId, AttendanceStatus.PERMISSION);
        long late = attendanceRepository.countByStudentSubject_IdAndStatus(studentSubjectId,AttendanceStatus.LATE);
        long deduction = 0;
        deduction += absent;
        deduction += permission / 2;
        deduction += late / 3;

        double score = 10 - deduction;
        return Math.max(score,0);
    }

    private GradeStatus calculateGrade(Double total) {
        if (total >= 85) return GradeStatus.A;
        if (total >= 75) return GradeStatus.B;
        if (total >= 50) return GradeStatus.C;
        return GradeStatus.F;
    }

    private Double calculateGPA(GradeStatus grade) {
        return switch (grade) {
            case A -> 4.0;
            case B -> 3.0;
            case C -> 2.0;
            case F -> 1.0;
        };
    }
}
