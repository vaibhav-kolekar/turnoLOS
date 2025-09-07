package com.example.los.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MockNotificationService implements NotificationService {
    private final Logger log = LoggerFactory.getLogger(MockNotificationService.class);

    @Override
    public void pushToAgent(Long agentId, String message) {
        log.info("MOCK_NOTIFICATION pushed to agent {}: {}", agentId, message);
    }

    @Override
    public void pushToManager(Long managerId, String message) {
        log.info("MOCK_NOTIFICATION pushed to manager {}: {}", managerId, message);
    }

    @Override
    public void smsToCustomer(String phone, String message) {
        log.info("MOCK_NOTIFICATION pushed to {}: {}", phone, message);
    }
}
