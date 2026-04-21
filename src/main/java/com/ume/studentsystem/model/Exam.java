package com.ume.studentsystem.model;

import com.ume.studentsystem.model.enums.ExamType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "exams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "term_id",foreignKey = @ForeignKey(name = "fk_exam_term"))
    private AcademicTerm term;

    @ManyToOne
    @JoinColumn(name = "classroom_id",foreignKey = @ForeignKey(name = "fk_exam_classroom"))
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "subject_id",foreignKey = @ForeignKey(name = "fk_exam_subject"))
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "room_id",foreignKey = @ForeignKey(name = "fk_exam_room"))
    private Room room;

    @ManyToOne
    @JoinColumn(name = "invigilator_id",foreignKey = @ForeignKey(name = "fk_exam_invigilator"))
    private Staff invigilator;

    @Enumerated(EnumType.STRING)
    private ExamType examType;

    private LocalDate examDate;

    private LocalTime startTime;
    private LocalTime endTime;

    private Integer totalMarks;
}
