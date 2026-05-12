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
@Table(name = "departments")
@EntityListeners(EntityAuditListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE departments SET deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Department extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

}
