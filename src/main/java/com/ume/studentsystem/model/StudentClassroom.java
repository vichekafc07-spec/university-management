package com.ume.studentsystem.model;

import com.ume.studentsystem.model.enums.StudentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "student_classrooms",uniqueConstraints = @UniqueConstraint(
        name = "uk_student_classrooms",
        columnNames = {"student_id","classroom_id"}
))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentClassroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    private Integer year;
    private Integer semester;
    private Integer generation;

    @Enumerated(EnumType.STRING)
    private StudentStatus status;

    private LocalDate assignedDate;
}
