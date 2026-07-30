package com.app.transactionservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false, updatable = false)
    private String senderAccountNumber;
    @Column(nullable = false, updatable = false)
    private String receiverAccountNumber;
    @Column(nullable = false, updatable = false, precision = 15, scale = 2)
    private BigDecimal amount;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private String description;
    private String failureReason;
    private String referenceNumber;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

}
