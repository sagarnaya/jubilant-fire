package com.bank.backend.repository;

import com.bank.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPasswordAndRole(String email, String password, String role);

    User findByEmailAndRole(String email, String role);

    User findByEmail(String email);
}
