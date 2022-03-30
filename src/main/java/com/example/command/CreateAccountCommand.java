package com.example.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateAccountCommand {
    @TargetAggregateIdentifier
    private String accountId;

    private Integer overdraftLimit;
}
