package com.example.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositMoneyCommand {
    @TargetAggregateIdentifier
    private String accountId;

    private String transactionId;

    private Integer amount;
}
