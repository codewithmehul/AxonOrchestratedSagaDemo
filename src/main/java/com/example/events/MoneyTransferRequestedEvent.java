package com.example.events;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class MoneyTransferRequestedEvent {
    private String transferId;
    private String sourceAccount;
    private String targetAccount;
    private Integer amount;
}
