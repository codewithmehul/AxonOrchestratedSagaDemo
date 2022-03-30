package com.example.events;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class MoneyWithdrawnEvent {
    private String accountId;
    private String transactionId;
    private Integer amount;
    private Integer balance;
}
