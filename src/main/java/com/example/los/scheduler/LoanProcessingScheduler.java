package com.example.los.scheduler;

import com.example.los.service.LoanProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoanProcessingScheduler {

    private final LoanProcessingService loanProcessingService;

    @Scheduled(fixedDelay = 5000)
    public void runLoanProcessing() {
        log.info("Triggering loan processing job...");
        loanProcessingService.processPendingLoans();
    }
}
