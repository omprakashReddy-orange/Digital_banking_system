package com.app.transactionservice.entity;

/**
 * Transaction lifecycle:
 * PENDING -> PROCESSING -> COMPLETED
 * PENDING -> PROCESSING -> PENDING_VERIFICATION(suspicious)
 *                                -> COMPLETED (verification)
 *                                -> FLAGGED (SAGA REFUND)
 *                       -> FAILED
 *                       ->FLAGGED
 *
 */
public enum TransactionStatus {
    PENDING,
    PROCESSING,
    COMPLETED,
    PENDING_VERIFICATION,
    FLAGGED,
    FAILED
}
