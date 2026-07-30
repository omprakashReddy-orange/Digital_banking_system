package com.app.transactionservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequest {
    @NotBlank(message = "Sender account number is required")
    private String senderAccountNumber;
    @NotBlank(message = "Receiver account number is required")
    private String receiverAccountNumber;
    @NotBlank(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;
    private String description;
}
