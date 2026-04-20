package com.ume.studentsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "timetables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "term_id",foreignKey = @ForeignKey(name = "fk_timetable_term"))
    private AcademicTerm term;

    @ManyToOne
    @JoinColumn(name = "classroom_id",foreignKey = @ForeignKey(name = "fk_classroom_term"))
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "subject_id",foreignKey = @ForeignKey(name = "fk_subject_term"))
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "lecturer_id",foreignKey = @ForeignKey(name = "fk_lecturer_term"))
    private Staff lecturer;

    @ManyToOne
    @JoinColumn(name = "room_id",foreignKey = @ForeignKey(name = "fk_timetable_term"))
    private Room room;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;
}
