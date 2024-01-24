package com.bank.backend.repository;

import com.bank.backend.model.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer> {
    @Query(value = "SELECT * FROM beneficiary WHERE sender_account = :accountId", nativeQuery = true)
    List<Beneficiary> findBeneficiaryByAccountId(@Param("accountId") Integer accountId);
}
