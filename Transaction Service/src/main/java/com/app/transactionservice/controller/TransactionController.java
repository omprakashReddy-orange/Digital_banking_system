package com.app.transactionservice.controller;

import com.app.transactionservice.dto.TransactionResponse;
import com.app.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
