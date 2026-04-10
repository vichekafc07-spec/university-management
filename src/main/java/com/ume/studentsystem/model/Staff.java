package com.ume.studentsystem.model;

import com.ume.studentsystem.config.EntityAuditListener;
import com.ume.studentsystem.model.enums.GenderStatus;
import com.ume.studentsystem.model.enums.StaffPosition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@EntityListeners(EntityAuditListener.class)
@Table(name = "staffs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE staffs SET deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted = false")
public class Staff extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String staffCode;
    private String personalEmail;
    private String phone;
    @Enumerated(EnumType.STRING)
    private GenderStatus gender;
    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private StaffPosition position;
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , unique = true)
    private AppUser user;

}
