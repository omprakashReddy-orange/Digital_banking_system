package com.app.accountservice.repository;

import com.app.accountservice.entity.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    boolean existsByAccountNumber(String accountNumber);

    boolean existsByEmail(String email);

    Optional<Account> findByAccountNumber(String accountNumber);
}
