package com.app.accountservice.DTO;

import com.app.accountservice.entity.AccountStatus;
import com.app.accountservice.entity.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {

    private String id;
    private String accountNumber;
    private String accountHolderName;
    private String email;
    private String phone;
    private AccountType type;
    private AccountStatus status;
    private BigDecimal balance;
    private BigDecimal dailyLimit;
    private LocalDateTime createdAt;
}
