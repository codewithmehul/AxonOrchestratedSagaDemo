package com.example.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteMoneyTransferCommand {
    @TargetAggregateIdentifier
    private String transferId;

    private String sourceAccount;
    private String targetAccount;
    private Integer amount;

}
