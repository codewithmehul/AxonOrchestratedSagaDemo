package com.example.aggregate;

import com.example.command.CancelMoneyTransferCommand;
import com.example.command.CompleteMoneyTransferCommand;
import com.example.command.RequestMoneyTransferCommand;
import com.example.events.MoneyTransferCancelledEvent;
import com.example.events.MoneyTransferCompletedEvent;
import com.example.events.MoneyTransferRequestedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

@NoArgsConstructor
@Aggregate
public class MoneyTransferAggregate {

    @AggregateIdentifier
    private String transferId;

    @CommandHandler
    public MoneyTransferAggregate(RequestMoneyTransferCommand cmd) {
        MoneyTransferRequestedEvent moneyTransferRequestedEvent = new MoneyTransferRequestedEvent();
        moneyTransferRequestedEvent.setTransferId(cmd.getTransferId());
        moneyTransferRequestedEvent.setSourceAccount(cmd.getSourceAccount());
        moneyTransferRequestedEvent.setTargetAccount(cmd.getTargetAccount());
        moneyTransferRequestedEvent.setAmount(cmd.getAmount());
        AggregateLifecycle.apply(moneyTransferRequestedEvent);
    }

    @EventSourcingHandler
    protected void on(MoneyTransferRequestedEvent event) {
        this.transferId = event.getTransferId();
    }

    @CommandHandler
    public void handle(CompleteMoneyTransferCommand cmd) {
        MoneyTransferCompletedEvent moneyTransferCompletedEvent = new MoneyTransferCompletedEvent();
        moneyTransferCompletedEvent.setTransferId(cmd.getTransferId());
        AggregateLifecycle.apply(moneyTransferCompletedEvent);
    }

    @EventSourcingHandler
    protected void on(MoneyTransferCompletedEvent event) {
        markDeleted();
    }

    @CommandHandler
    public void handle(CancelMoneyTransferCommand cmd) {
        MoneyTransferCancelledEvent moneyTransferCancelledEvent = new MoneyTransferCancelledEvent();
        moneyTransferCancelledEvent.setTransferId(cmd.getTransferId());
        AggregateLifecycle.apply(moneyTransferCancelledEvent);
    }
}
