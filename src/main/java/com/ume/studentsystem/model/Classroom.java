package com.ume.studentsystem.model;

import com.ume.studentsystem.config.EntityAuditListener;
import com.ume.studentsystem.model.audit.AuditEntity;
import com.ume.studentsystem.model.enums.StudyTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classrooms", uniqueConstraints = @UniqueConstraint(
        columnNames = {"name","year","semester"}
))
@EntityListeners(EntityAuditListener.class)
@SQLDelete(sql = "UPDATE classrooms SET deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Classroom extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer year;

    private Integer generation;

    private Integer semester;

    @Enumerated(EnumType.STRING)
    private StudyTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", foreignKey = @ForeignKey(name = "fk_classroom_department"))
    private Department department;

    @ManyToMany
    @JoinTable(
            name = "classroom_subjects",
            joinColumns = @JoinColumn(name = "classroom_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects = new HashSet<>();
}
