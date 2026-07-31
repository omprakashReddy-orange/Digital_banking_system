package com.app.accountservice.Service;

import com.app.accountservice.DTO.AccountResponse;
import com.app.accountservice.DTO.CreateAccountRequest;
import com.app.accountservice.entity.Account;
import com.app.accountservice.entity.AccountStatus;
import com.app.accountservice.entity.AccountType;
import com.app.accountservice.mapper.AccountMapper;
import com.app.accountservice.repository.AccountRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * Creates a new account with the given request details.
     *
     * @param request the account creation request
     * @return the created account response
     */
    public AccountResponse createAccount(@Valid CreateAccountRequest request) {
        log.info("Creating account with request: {}", request.getEmail());
        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Account with email " + request.getEmail() + " already exists");
        }

        Account acc = Account.builder()
                .accountHolderName(request.getAccountHolderName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .type(request.getType())
                .balance(request.getInitialDeposit())
                .accountNumber(generateAccountNumber())
                .status(AccountStatus.ACTIVE)
                .dailyLimit(
                        request.getType() == AccountType.SAVINGS ? new BigDecimal("100000.00") : new BigDecimal("500000.00")
                )
                .build();
        Account savedAccount = accountRepository.save(acc);
        log.info("Account created successfully with ID: {}", savedAccount.getId());


        return AccountMapper.MapToDto(savedAccount);

    }

    /**
     * Generates a unique account number.
     *
     * @return the generated account number
     */
    private String generateAccountNumber() {
        // Generate unique 12 digit number uniquely and securely
        String accountNumber;

        do{
            long number = secureRandom.nextLong(1_000_000_000_000_000L);
            accountNumber = String.format("%012d", number);
            log.info("Generated account number: {}", accountNumber);
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

    /**
     * Deducts the specified amount from the account with the given account number.
     *
     * @param accountNumber the account number of the account to deduct from
     * @param amount        the amount to deduct
     */
    public void deductBalance(String accountNumber, BigDecimal amount) {
        log.info("Attempting to deduct {} from account number: {}", amount, accountNumber);
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account with number " + accountNumber + " not found"));
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance in account number " + accountNumber);
        }
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException("Account number " + accountNumber + " is not active");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        log.info("Balance deducted successfully for account number: {}. New balance: {}", account.getAccountNumber(), account.getBalance());
    }

    /**
     * Retrieves the account with the given ID.
     *
     * @param accountId the ID of the account
     * @return the account details
     */
    public AccountResponse getAccountById(String accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with ID " + accountId + " not found"));
        log.info("Account retrieved successfully with ID: {}", account.getId());
        return AccountMapper.MapToDto(account);
    }

    /**
     * Retrieves the balance of the account with the given ID.
     *
     * @param accountId the ID of the account
     * @return the balance of the account
     */
    public BigDecimal getAccountBalance(String accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with ID " + accountId + " not found"));
        log.info("Account Balance retrieved successfully with ID: {}", account.getId());
        return account.getBalance();
    }

    /**
     * Blocks the account with the given ID.
     *
     * @param accountId the ID of the account to block
     */
    public void blockAccount(String accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with ID " + accountId + " not found"));
        account.setStatus(AccountStatus.BLOCKED);
        accountRepository.save(account);
        log.info("Account blocked successfully with ID: {}", account.getId());
    }

    /**
     * Credits the specified amount to the account with the given ID.
     * called by TRANSACTION SERVICE via Kafka
     *
     * @param accountId the ID of the account to credit
     * @param amount    the amount to credit
     */
    public void creditBalance(String accountId, BigDecimal amount) {
        log.info("Crediting balance for account ID: {} with amount: {}", accountId, amount);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with ID " + accountId + " not found"));
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        log.info("Balance credited successfully for account ID: {}. New balance: {}", account.getId(), account.getBalance());

    }

}
