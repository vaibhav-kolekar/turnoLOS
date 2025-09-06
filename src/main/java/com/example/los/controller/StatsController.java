package com.example.los.controller;

import com.example.los.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class StatsController {
    @Autowired
    private LoanService loanService;

    @GetMapping("/top")
    public ResponseEntity<?> topCustomers() {
        return ResponseEntity.ok(loanService.topCustomers(3));
    }
}
