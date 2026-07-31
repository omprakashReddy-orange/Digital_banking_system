package com.app.accountservice.mapper;

import com.app.accountservice.DTO.AccountResponse;
import com.app.accountservice.entity.Account;

public class AccountMapper {

    public static  AccountResponse MapToDto(Account account) {
        return AccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .accountHolderName(account.getAccountHolderName())
                .email(account.getEmail())
                .phone(account.getPhone())
                .type(account.getType())
                .status(account.getStatus())
                .balance(account.getBalance())
                .dailyLimit(account.getDailyLimit())
                .createdAt(account.getCreatedAt())
                .build();
    }
}
