package com.bank.backend.repository;

import com.bank.backend.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    @Query(value = "SELECT * FROM Card a WHERE account_id = :accountId", nativeQuery = true)
    List<Card> findByAccountId(@Param("accountId") Integer accountId);
}
