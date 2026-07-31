package com.app.accountservice.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountEventConsumer {

    private final AccountService accountService;

    /**
     * Consumes account-related events from Kafka and processes them accordingly.
     * Credit receiver account
     * @param event the event data
     */
    @KafkaListener(topics = "transaction.complete")
    public void consumeAccountEvent(@Payload Map<String, Object> event) {
        log.info("Consumed account event: {}", event);

        try{
            String receiverAccountNumber = (String) event.get("receiverAccountNumber");
            BigDecimal amount = new BigDecimal((String) event.get("amount").toString());
            log.info("Crediting account {} with amount {}", receiverAccountNumber, amount);
            accountService.creditBalance(receiverAccountNumber, amount);
        }catch(Exception e){
            log.error("Error processing account event: {}", e.getMessage());
        }

    }

    @KafkaListener(topics = "fraud.detected")
    public void consumeFraudEvent(@Payload Map<String, Object> event) {
        log.info("Consumed fraud event: {}", event);

        try{
            String senderAccountNumber = (String) event.get("senderAccountNumber");
            BigDecimal amount = new BigDecimal((String) event.get("amount").toString());
            log.info("Blocking account {} due to fraud detection", senderAccountNumber);
            accountService.blockAccount(senderAccountNumber);
            log.info("Refunding amount {} to sender account {}", amount, senderAccountNumber);
            accountService.creditBalance(senderAccountNumber, amount);
        }catch(Exception e){
            log.error("Error processing fraud event: {}", e.getMessage());
        }

    }
}
