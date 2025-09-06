package com.example.los.notification;

public interface NotificationService {
    void pushToAgent(Long agentId, String message);
    void pushToManager(Long managerId, String message);
    void smsToCustomer(String phone, String message);
}
