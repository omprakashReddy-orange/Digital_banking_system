package com.app.accountservice.controller;


import com.app.accountservice.DTO.AccountResponse;
import com.app.accountservice.DTO.CreateAccountRequest;
import com.app.accountservice.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        log.info("Received request to create account: {}", request);
        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable String accountId) {
        log.info("Received request to get account by ID: {}", accountId);
        AccountResponse response = accountService.getAccountById(accountId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable String accountId) {
        log.info("Received request to get account balance by ID: {}", accountId);
        BigDecimal balance = accountService.getAccountBalance(accountId);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/{accountId}/block")
    public ResponseEntity<String> blockAccount(@PathVariable String accountId) {
        log.info("Received request to block account by ID: {}", accountId);
        accountService.blockAccount(accountId);
        return ResponseEntity.ok("Account blocked successfully");
    }

    /*
    *
    * SAGA - compensating transaction endpoint
    * CALLED BY TRANSACTION SERVICE in TWO SCENARIOS
    * 1. FRAUD detected -> refund sender
    * 2. Transaction complete -> credit receiver
    * */
    @PostMapping("/{accountId}/credit")
    public ResponseEntity<String> compensateTransaction(@PathVariable String accountId, @RequestParam BigDecimal amount) {
        log.info("Received request to compensate transaction for account ID: {}", accountId);
        accountService.creditBalance(accountId, amount);
        return ResponseEntity.ok("Transaction compensation successful");
    }





}
