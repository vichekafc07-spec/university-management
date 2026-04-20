package com.ume.studentsystem.model;

import com.ume.studentsystem.model.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@EntityListeners(EntityListeners.class)
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE rooms SET deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted = false")
public class Room extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer capacity;
    private String building;
}
