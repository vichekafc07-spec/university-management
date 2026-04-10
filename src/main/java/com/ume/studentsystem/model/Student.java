package com.ume.studentsystem.model;

import com.ume.studentsystem.model.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String studentCode;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private GenderStatus gender;

    private LocalDate dateOfBirth;

    private String phone;

    private String email;

    @Enumerated(EnumType.STRING)
    private ProgramType programType;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private String major;

    private String enrollmentYear;

    private Integer generation;

    @Enumerated(EnumType.STRING)
    private StudyTime studyTime;

    @Enumerated(EnumType.STRING)
    private StudentStatus status;
}
