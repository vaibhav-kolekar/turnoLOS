# Loan Origination System (LOS) – Spring Boot

This is a **Loan Origination System (LOS)** built with Spring Boot that allows safe, concurrent processing of loan applications. It:

- Provides RESTful APIs for submitting loans, agent decisions, status counts, top customers, and paginated loan retrieval.
- Simulates background processing delays (~25 seconds) using a thread-pool-based processor.
- Offers a mock `NotificationService` that logs notifications (e.g., push or SMS).
- Utilizes an in-memory H2 database by default, with optional configuration to use MySQL or PostgreSQL.
- Includes a Postman collection for easy API testing.

---

##  Getting Started

### Prerequisites

Ensure your development environment includes:

- **Java 17** or higher
- **Apache Maven**
- (Optional) MySQL or PostgreSQL if choosing an external database over the default H2

### Running Locally

1. Clone the repository:
   ```bash
   git clone https://github.com/vaibhav-kolekar/turnoLOS.git
   cd turnoLOS
   
2. Run command
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

