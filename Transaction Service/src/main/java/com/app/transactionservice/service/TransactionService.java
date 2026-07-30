package com.app.transactionservice.service;

import com.app.transactionservice.dto.TransactionResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionService {
    public TransactionResponse createTransaction(@Valid TransactionResponse transactionRequest) {
    }
}
