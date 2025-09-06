# Loan Origination System (LOS) - Spring Boot

## What is included
- REST APIs to submit loans, agent decisions, status counts, top customers and fetch loans by status with pagination.
- Background thread-pool based processor that simulates system approval with delays and assigns loans to agents when needed.
- Mock `NotificationService` that logs notifications (push/SMS).
- Uses H2 in-memory DB by default. Instructions to switch to MySQL/Postgres provided.
- Postman collection included.

## Run locally
1. Java 17+, Maven installed.
2. Build & run:
```bash
mvn spring-boot:run
```
3. The app runs on http://localhost:8080

## Endpoints
- `POST /api/v1/loans` - submit loan
- `GET /api/v1/loans?status={status}&page={n}&size={m}` - fetch loans
- `GET /api/v1/loans/status-count` - counts by status
- `PUT /api/v1/agents/{agentId}/loans/{loanId}/decision` - agent decision
- `GET /api/v1/customers/top` - top 3 customers by approved loans

## Notes
- Background processing runs automatically and simulates delays (~25 seconds) before deciding system outcome.
- For production use switch to MySQL/Postgres and tune thread pool and transactional settings.

