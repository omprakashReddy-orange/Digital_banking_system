package com.app.transactionservice.controller;

import com.app.transactionservice.dto.TransactionResponse;
import com.app.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;

    /**
     * Creates a new transaction based on the provided transaction request.
     * @param transactionRequest the transaction request containing the details of the transaction to be created
     * @return TransactionResponse a ResponseEntity containing the created transaction response
     */
    @PostMapping
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransactionResponse transactionRequest) {
        log.info("Creating transaction: {}", transactionRequest);
        TransactionResponse transactionResponse = transactionService.createTransaction(transactionRequest);
        return ResponseEntity.ok(transactionResponse);
    }

    /**
     * get transaction by id
     * @param transId transaction id
     * @return transaction response
     */
    @GetMapping("/{transId}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable String transId){
        log.info("Fetching transaction with ID: {}", transId);
        TransactionResponse transactionResponse = transactionService.getTransactionById(transId);
        return ResponseEntity.ok(transactionResponse);
    }

    /**
     * get transaction history by account id
     * @param accountId to get transaction history
     * @return list of transactions response
     */
    @GetMapping("/account/{accountId}")
    public ResponseEntity<TransactionResponse> getTransactionHistory(@PathVariable String accountId){
        log.info("Fetching transaction history for account ID: {}", accountId);
        return ResponseEntity.ok(transactionService.getTransactionHistory(accountId));
    }



}
