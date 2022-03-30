package com.example.events;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class MoneyTransferCancelledEvent {
    private String transferId;
}
