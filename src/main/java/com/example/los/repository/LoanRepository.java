package com.example.los.repository;

import com.example.los.model.Loan;
import com.example.los.model.LoanStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Page<Loan> findByApplicationStatus(LoanStatus status, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select l from Loan l where l.applicationStatus = :status")
    List<Loan> pickForProcessing(@Param("status") LoanStatus status);

    long countByApplicationStatus(LoanStatus status);

    @Query("select l.customerName as name, count(l) as cnt from Loan l where l.applicationStatus in :approved group by l.customerName order by cnt desc")
    List<Object[]> topCustomers(@Param("approved") List<com.example.los.model.LoanStatus> approved);
}
