package com.ume.studentsystem.model;

import com.ume.studentsystem.model.enums.GradeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grades",uniqueConstraints = @UniqueConstraint(
        name = "uk_grade_student-subject",
        columnNames = {"student_subject_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "student_subject_id",foreignKey = @ForeignKey(name = "fk_grade_student-subject"))
    private StudentSubject studentSubject;

    private Double attendanceScore;

    private Double assignmentScore;
    private Double midtermScore;
    private Double finalScore;

    private Double totalScore;

    @Enumerated(EnumType.STRING)
    private GradeStatus grade;

    private Double gpa;
}
