package com.ume.studentsystem.repository;

import com.ume.studentsystem.model.BlackListedToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BlackListedTokenRepository extends JpaRepository<BlackListedToken, Long> {

    boolean existsByToken(String token);

    @Transactional
    @Modifying
    @Query("DELETE FROM BlackListedToken b WHERE b.expiresAt < CURRENT_TIMESTAMP")
    void deleteExpiredTokens();
}