package com.ume.studentsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "blacklisted_tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlackListedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false, unique = true)
    private String token;

    private Date expiresAt;
}
