package com.ume.studentsystem.config;

import com.ume.studentsystem.repository.BlackListedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class BlackListedTokenCleanupScheduler {

    private final BlackListedTokenRepository blackListedTokenRepository;

    @Scheduled(fixedRate = 3600000) // runs every 1 hour
    public void cleanExpiredTokens() {
        blackListedTokenRepository.deleteExpiredTokens();
    }
}
