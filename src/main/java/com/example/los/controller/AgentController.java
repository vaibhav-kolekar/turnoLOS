package com.example.los.controller;

import com.example.los.model.Loan;
import com.example.los.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/agents")
public class AgentController {
    @Autowired
    private AgentService agentService;

    @PutMapping("/{agentId}/loans/{loanId}/decision")
    public ResponseEntity<?> decision(@PathVariable Long agentId, @PathVariable Long loanId, @RequestBody DecisionRequest req) {
        return agentService.agentDecision(agentId, loanId, req.getDecision())
                .<ResponseEntity<?>>map(l -> ResponseEntity.ok(l))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agent or loan not found"));
    }

    public static class DecisionRequest { 
        private String decision;
        public String getDecision() { return decision; }
        public void setDecision(String decision) { this.decision = decision; }
    }
}
