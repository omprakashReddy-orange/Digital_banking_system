package com.app.accountservice.DTO;

import com.app.accountservice.entity.AccountStatus;
import com.app.accountservice.entity.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountRequest {

    @NotBlank(message = "Account holder name is required")
    private String accountHolderName;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Phone number is required")
    private String phone;
    @NotNull(message = "Account type is required")
    @Enumerated(EnumType.STRING)
    private AccountType type;
    @NotNull(message = "Initial deposit is required")
    @Positive(message = "Initial deposit must be a positive value")
    private BigDecimal initialDeposit;
}
