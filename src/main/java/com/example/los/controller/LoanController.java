package com.example.los.controller;

import com.example.los.model.Loan;
import com.example.los.model.LoanStatus;
import com.example.los.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        Loan created = loanService.submitLoan(loan);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<Loan>> getByStatus(@RequestParam("status") LoanStatus status,
                                                  @RequestParam(value="page", defaultValue="0") int page,
                                                  @RequestParam(value="size", defaultValue="10") int size) {
        return ResponseEntity.ok(loanService.fetchByStatus(status, page, size));
    }

    @GetMapping("/status-count")
    public ResponseEntity<Map<LoanStatus, Long>> statusCounts() {
        return ResponseEntity.ok(loanService.statusCounts());
    }
}
