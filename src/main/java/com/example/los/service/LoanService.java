package com.example.los.service;

import com.example.los.model.Loan;
import com.example.los.model.LoanStatus;
import com.example.los.model.LoanType;
import com.example.los.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    public Loan submitLoan(Loan loan) {
        loan.setCreatedAt(Instant.now());
        loan.setApplicationStatus(LoanStatus.APPLIED);
        if (loan.getLoanId() == null) loan.setLoanId(UUID.randomUUID().toString());
        return loanRepository.save(loan);
    }

    public Page<Loan> fetchByStatus(LoanStatus status, int page, int size) {
        Pageable p = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return loanRepository.findByApplicationStatus(status, p);
    }

    public Map<LoanStatus, Long> statusCounts() {
        Map<LoanStatus, Long> map = new EnumMap<>(LoanStatus.class);
        for (LoanStatus s : LoanStatus.values()) {
            map.put(s, loanRepository.countByApplicationStatus(s));
        }
        return map;
    }

    @Transactional
    public Optional<Loan> updateStatus(Long loanId, LoanStatus status) {
        Optional<Loan> o = loanRepository.findById(loanId);
        o.ifPresent(l -> {
            l.setApplicationStatus(status);
            loanRepository.save(l);
        });
        return o;
    }

    public List<Map<String,Object>> topCustomers(int topN) {
        List<com.example.los.model.LoanStatus> approved = Arrays.asList(LoanStatus.APPROVED_BY_SYSTEM, LoanStatus.APPROVED_BY_AGENT);
        List<Object[]> rows = loanRepository.topCustomers(approved);
        List<Map<String,Object>> res = new ArrayList<>();
        for (int i=0;i<Math.min(topN, rows.size());i++){
            Object[] r = rows.get(i);
            Map<String,Object> m = new HashMap<>();
            m.put("customerName", r[0]);
            m.put("approvedCount", ((Number)r[1]).longValue());
            res.add(m);
        }
        return res;
    }
}
