package com.example.events;

import lombok.Data;

@Data
public abstract class BalanceUpdatedEvent {
    private String accountId;

    private Integer balance;
}
