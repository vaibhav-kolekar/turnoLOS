package com.example.los.service;

import com.example.los.model.Loan;
import com.example.los.model.LoanStatus;
import com.example.los.repository.LoanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanProcessingService {

    private final LoanRepository loanRepository;

    @Transactional
    public void processPendingLoans() {
        try {
            List<Loan> loans = loanRepository.pickForProcessing(LoanStatus.NEW);
            log.info("Found {} loans to process", loans.size());

            for (Loan loan : loans) {
                if (loan.getLoanAmount() < 10000) {
                    loan.setApplicationStatus(LoanStatus.APPROVED_BY_SYSTEM);
                } else {
                    loan.setApplicationStatus(LoanStatus.REJECTED_BY_SYSTEM);
                }
                loanRepository.save(loan);
            }
        } catch (Exception e) {
            log.error("Error while processing loans", e);
        }
    }

}
