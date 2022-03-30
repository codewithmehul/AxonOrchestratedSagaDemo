package com.example.events;

import lombok.Data;

@Data
public class AccountCreatedEvent {
    private String accountId;

    private Integer overdraftLimit;
}
