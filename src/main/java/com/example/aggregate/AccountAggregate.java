package com.example.aggregate;

import com.example.command.CreateAccountCommand;
import com.example.command.DepositMoneyCommand;
import com.example.command.WithdrawMoneyCommand;
import com.example.events.AccountCreatedEvent;
import com.example.events.MoneyDepositedEvent;
import com.example.events.MoneyWithdrawnEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;

    private int balance;
    private int overdraftLimit;

    @CommandHandler
    public AccountAggregate(CreateAccountCommand cmd) {
        apply(new AccountCreatedEvent(cmd.getAccountId(), cmd.getOverdraftLimit()))
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
        apply(new MoneyWithdrawnEvent(accountId, cmd.getTransactionId(), cmd.getAmount(), balance - cmd.getAmount()));
    }

    @CommandHandler
    public void handle(DepositMoneyCommand cmd) {
        apply(new MoneyDepositedEvent(accountId, cmd.getTransactionId(), cmd.getAmount(), balance + cmd.getAmount()));
    }

    @EventSourcingHandler
    protected void on(BalanceUpdatedEvent event) {
        this.balance = event.getBalance();
    }

}
}
