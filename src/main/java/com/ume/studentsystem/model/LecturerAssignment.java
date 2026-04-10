package com.ume.studentsystem.model;

import com.ume.studentsystem.model.enums.StudyTime;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "lecturer_assignments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"lecturer_id", "classroom_id", "subject_id", "time"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturerAssignment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Staff lecturer;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Enumerated(EnumType.STRING)
    private StudyTime time;

    private LocalDate assignedDate;
}
