package com.example.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.stereotype.Component;

@Data
@Builder
public class RequestMoneyTransferCommand {
    @TargetAggregateIdentifier
    private String transferId;
    private String sourceAccount;
    private String targetAccount;
    private Integer amount;
}
