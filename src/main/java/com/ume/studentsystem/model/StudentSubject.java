package com.ume.studentsystem.model;

import com.ume.studentsystem.model.enums.StudentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "student_subjects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id",foreignKey = @ForeignKey(name = "fk_student_subjects_student"))
    private StudentClassroom studentClassroom;

    @ManyToOne
    @JoinColumn(name = "subject_id" , foreignKey = @ForeignKey(name = "fk_student_subjects_subject"))
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "classroom_id", foreignKey = @ForeignKey(name = "fk_student_subjects_classroom"))
    private Classroom classroom;

    private LocalDate enrollDate;

    @Enumerated(EnumType.STRING)
    private StudentStatus status;
}
