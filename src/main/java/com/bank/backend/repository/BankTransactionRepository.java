package com.bank.backend.repository;

import com.bank.backend.model.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Integer> {

    @Query(value = "select * from bank_transaction", nativeQuery = true)
    List<BankTransaction> findAllBankTransactions();

    @Query(value = "SELECT * FROM bank_transaction WHERE sender_account = :accountId" +
            " or receiver_account = :accountId", nativeQuery = true)
    List<BankTransaction> findLoanByAccountId(@Param("accountId") Integer accountId);
}
