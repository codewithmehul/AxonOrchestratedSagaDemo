package com.example.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class DepositMoneyCommand {
    @TargetAggregateIdentifier
    private String accountId;

    private String transactionId;

    private Integer amount;
}