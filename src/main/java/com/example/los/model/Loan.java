package com.example.los.model;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "loans", indexes = {
    @Index(columnList = "applicationStatus"),
    @Index(columnList = "customerName")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanId;
    private String customerName;
    private String customerPhone;
    private Double loanAmount;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @Enumerated(EnumType.STRING)
    private LoanStatus applicationStatus;

    private Instant createdAt;

    @Version
    private Long version;
}
