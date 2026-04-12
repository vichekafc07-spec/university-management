package com.ume.studentsystem.model;

import com.ume.studentsystem.model.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "attendances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_subject_id", foreignKey = @ForeignKey(name = "fk_attendance_student-subject"))
    private StudentSubject studentSubject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id" , foreignKey = @ForeignKey(name = "fk_attendance_session"))
    private Session session;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}
