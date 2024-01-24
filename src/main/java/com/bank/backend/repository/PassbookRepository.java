package com.bank.backend.repository;

import com.bank.backend.model.Passbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassbookRepository extends JpaRepository<Passbook, Integer> {

    @Query(value = "SELECT * FROM Passbook a WHERE account_id = :accountId", nativeQuery = true)
    List<Passbook> findPassbookByAccountId(@Param("accountId") Integer accountId);
}
