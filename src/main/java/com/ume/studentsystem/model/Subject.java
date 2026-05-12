package com.ume.studentsystem.model;

import com.ume.studentsystem.config.EntityAuditListener;
import com.ume.studentsystem.model.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@EntityListeners(EntityAuditListener.class)
@Getter
@Setter
@Table(name = "subjects")
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE subjects SET deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Subject extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String title;
    private int credit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
