package com.ume.studentsystem.model;

import com.ume.studentsystem.config.EntityAuditListener;
import com.ume.studentsystem.model.audit.AuditEntity;
import com.ume.studentsystem.model.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@EntityListeners(EntityAuditListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE students SET deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Student extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    private String photoUrl;

    @Enumerated(EnumType.STRING)
    private StudyTime studyTime;

    @Enumerated(EnumType.STRING)
    private StudentStatus status;
}
