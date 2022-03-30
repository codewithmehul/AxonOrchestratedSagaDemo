package com.example.aggregate;

import com.example.command.CreateAccountCommand;
import com.example.command.DepositMoneyCommand;
import com.example.command.WithdrawMoneyCommand;
import com.example.events.AccountCreatedEvent;
import com.example.events.BalanceUpdatedEvent;
import com.example.events.MoneyDepositedEvent;
import com.example.events.MoneyWithdrawnEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
//import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;


@Aggregate
@NoArgsConstructor
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;

    private int balance;
    private int overdraftLimit;

    @CommandHandler
    public AccountAggregate(CreateAccountCommand cmd) {
        AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent();
        accountCreatedEvent.setAccountId(cmd.getAccountId());
        accountCreatedEvent.setOverdraftLimit(cmd.getOverdraftLimit());
        AggregateLifecycle.apply(accountCreatedEvent);
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent event) {
        this.accountId = event.getAccountId();
        this.overdraftLimit = event.getOverdraftLimit();
    }

    @CommandHandler
    public void handle(WithdrawMoneyCommand cmd) throws Exception {
        if (balance + overdraftLimit < cmd.getAmount()) {
            throw new Exception();
        }

        MoneyWithdrawnEvent moneyWithdrawnEvent = new MoneyWithdrawnEvent();
        moneyWithdrawnEvent.setTransactionId(cmd.getTransactionId());
        moneyWithdrawnEvent.setAmount(cmd.getAmount());
        moneyWithdrawnEvent.setBalance(balance - cmd.getAmount());

        AggregateLifecycle.apply(moneyWithdrawnEvent);
       // apply(new MoneyWithdrawnEvent(accountId, cmd.getTransactionId(), cmd.getAmount(), balance - cmd.getAmount()));
    }

    @CommandHandler
    public void handle(DepositMoneyCommand cmd) {
        MoneyDepositedEvent moneyDepositedEvent = new MoneyDepositedEvent();
        moneyDepositedEvent.setTransactionId(cmd.getTransactionId());
        moneyDepositedEvent.setAmount(cmd.getAmount());
        moneyDepositedEvent.setBalance(balance - cmd.getAmount());
    }

    @EventSourcingHandler
    protected void on(BalanceUpdatedEvent event) {
        this.balance = event.getBalance();
    }

}
