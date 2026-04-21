package com.ume.studentsystem.service.impl;

import com.ume.studentsystem.dto.request.ExamResultBulkRequest;
import com.ume.studentsystem.dto.request.ExamResultRequest;
import com.ume.studentsystem.dto.response.ExamResultResponse;
import com.ume.studentsystem.exceptions.BadRequestException;
import com.ume.studentsystem.exceptions.ResourceNotFoundException;
import com.ume.studentsystem.mapper.ExamResultMapper;
import com.ume.studentsystem.model.Exam;
import com.ume.studentsystem.model.ExamResult;
import com.ume.studentsystem.model.StudentSubject;
import com.ume.studentsystem.repository.ExamRepository;
import com.ume.studentsystem.repository.ExamResultRepository;
import com.ume.studentsystem.repository.StudentSubjectRepository;
import com.ume.studentsystem.service.ExamResultService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExamResultServiceImpl implements ExamResultService {

    private final ExamRepository examRepository;
    private final StudentSubjectRepository studentSubjectRepository;
    private final ExamResultRepository examResultRepository;
    private final ExamResultMapper mapper;

    @Override
    public ExamResultResponse save(ExamResultRequest request) {

        var exam = examRepository.findById(request.examId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));

        var ss = studentSubjectRepository.findById(request.studentSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("StudentSubject not found"));

        validateScore(request.score(), exam.getTotalMarks());
        validateExamOwnership(exam, ss);

        var entity = examResultRepository
                .findByExam_IdAndStudentSubject_Id(
                        request.examId(),
                        request.studentSubjectId()
                )
                .orElse(new ExamResult());

        entity.setExam(exam);
        entity.setStudentSubject(ss);
        entity.setScore(request.score());
        entity.setRemark(request.remark());

        examResultRepository.save(entity);

        return mapper.toResponse(entity);
    }

    @Override
    public List<ExamResultResponse> saveBulk(ExamResultBulkRequest request) {

        var exam = examRepository.findById(request.examId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));

        List<ExamResultResponse> responses = new ArrayList<>();

        for (var item : request.students()) {

            var ss = studentSubjectRepository.findById(item.studentSubjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("StudentSubject not found"));

            validateScore(item.score(), exam.getTotalMarks());
            validateExamOwnership(exam, ss);

            var entity = examResultRepository
                    .findByExam_IdAndStudentSubject_Id(
                            request.examId(),
                            item.studentSubjectId()
                    )
                    .orElse(new ExamResult());

            entity.setExam(exam);
            entity.setStudentSubject(ss);
            entity.setScore(item.score());
            entity.setRemark(item.remark());

            examResultRepository.save(entity);

            responses.add(mapper.toResponse(entity));
        }

        return responses;
    }

    @Override
    public List<ExamResultResponse> getByExam(Long examId) {
        return examResultRepository.findByExam_Id(examId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ExamResultResponse> getByStudentSubject(Long studentSubjectId) {
        return examResultRepository.findByStudentSubject_Id(studentSubjectId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (!examResultRepository.existsById(id)) {
            throw new ResourceNotFoundException("Result not found");
        }
        examResultRepository.deleteById(id);
    }

    private void validateScore(Double score, Integer max) {
        if (score < 0 || score > max) {
            throw new BadRequestException("Score must be between 0 and " + max);
        }
    }

    private void validateExamOwnership(Exam exam, StudentSubject ss) {

        if (!exam.getSubject().getId().equals(ss.getSubject().getId())) {
            throw new BadRequestException("Subject mismatch");
        }

        if (!exam.getClassroom().getId()
                .equals(ss.getStudentClassroom().getClassroom().getId())) {
            throw new BadRequestException("Student not in exam classroom");
        }
    }
}
