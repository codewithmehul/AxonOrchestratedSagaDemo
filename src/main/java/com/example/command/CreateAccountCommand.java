package com.example.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
public class CreateAccountCommand {
    @TargetAggregateIdentifier
    private String accountId;

    private Integer overdraftLimit;
}
