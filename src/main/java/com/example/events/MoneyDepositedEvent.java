package com.example.events;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class MoneyDepositedEvent {
    private String accountId;
    private String transactionId;
    private Integer amount;
    private Integer balance;
}
