package com.bank.backend.repository;

import com.bank.backend.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    @Query(value = "SELECT * FROM Loan WHERE account_id = :accountId", nativeQuery = true)
    List<Loan> findLoanByAccountId(@Param("accountId") Integer accountId);

    @Modifying
    @Query(value = "update loan set status=:status where loan_id= :loanId", nativeQuery = true)
    Integer updateLoanStatus(@Param("status") String status, @Param("loanId") Integer loanId);
}
