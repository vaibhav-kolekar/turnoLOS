package com.example.los.service;

import com.example.los.model.Agent;
import com.example.los.model.Loan;
import com.example.los.model.LoanStatus;
import com.example.los.repository.AgentRepository;
import com.example.los.repository.LoanRepository;
import com.example.los.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AgentService {
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Optional<Agent> assignAgentToLoan(Long loanId) {
        List<Agent> agents = agentRepository.findAll();
        Optional<Agent> opt = agents.stream().filter(Agent::isAvailable).findFirst();
        if (opt.isEmpty()) return Optional.empty();
        Agent agent = opt.get();
        agent.setAvailable(false);
        agentRepository.save(agent);

        notificationService.pushToAgent(agent.getId(), "New loan assigned: " + loanId);
        if (agent.getManager() != null) {
            notificationService.pushToManager(agent.getManager().getId(), "Loan assigned to your agent: " + loanId);
        }
        loanRepository.findById(loanId).ifPresent(l -> {
            l.setApplicationStatus(LoanStatus.UNDER_REVIEW);
            loanRepository.save(l);
        });
        return Optional.of(agent);
    }

    @Transactional
    public Optional<Loan> agentDecision(Long agentId, Long loanId, String decision) {
        Optional<Agent> a = agentRepository.findById(agentId);
        Optional<Loan> ln = loanRepository.findById(loanId);
        if (a.isEmpty() || ln.isEmpty()) return Optional.empty();
        Loan loan = ln.get();
        if (decision.equalsIgnoreCase("APPROVE")) {
            loan.setApplicationStatus(LoanStatus.APPROVED_BY_AGENT);
            loanRepository.save(loan);
            Agent agent = a.get();
            agent.setAvailable(true);
            agentRepository.save(agent);
            notificationService.smsToCustomer(loan.getCustomerPhone(), "Your loan was approved by agent.");
        } else {
            loan.setApplicationStatus(LoanStatus.REJECTED_BY_AGENT);
            loanRepository.save(loan);
            Agent agent = a.get();
            agent.setAvailable(true);
            agentRepository.save(agent);
            notificationService.smsToCustomer(loan.getCustomerPhone(), "Your loan was rejected by agent.");
        }
        return Optional.of(loan);
    }
}
