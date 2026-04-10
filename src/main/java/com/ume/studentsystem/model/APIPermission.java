package com.ume.studentsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "api_permissions")
@Getter
@Setter
@NoArgsConstructor
public class APIPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String httpMethod;
    private String path;
    private String permission;
}
