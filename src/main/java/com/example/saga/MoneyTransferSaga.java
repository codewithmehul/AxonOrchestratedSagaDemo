package com.example.saga;

import com.example.command.CancelMoneyTransferCommand;
import com.example.command.CompleteMoneyTransferCommand;
import com.example.command.DepositMoneyCommand;
import com.example.command.WithdrawMoneyCommand;
import com.example.events.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;

import static org.axonframework.modelling.saga.SagaLifecycle.end;

@Saga
@Slf4j
public class MoneyTransferSaga {
    @Autowired
    private transient CommandGateway commandGateway;

    private String targetAccount;
    private String transferId;

    @StartSaga
    @SagaEventHandler(associationProperty = "transferId")
    public void on(MoneyTransferRequestedEvent event) {
        targetAccount = event.getTargetAccount();
        transferId = event.getTransferId();
        SagaLifecycle.associateWith("transactionId", transferId);
        commandGateway.send(new WithdrawMoneyCommand(event.getSourceAccount(), transferId, event.getAmount()),
                new CommandCallback<WithdrawMoneyCommand, Object>() {

                    @Override
                    public void onResult(CommandMessage<? extends WithdrawMoneyCommand> commandMessage, CommandResultMessage<? extends Object> result) {

                    }

                    @Override
                    public void onFailure(CommandMessage<? extends WithdrawMoneyCommand> commandMessage, Throwable cause) {
                        commandGateway.send(new CancelMoneyTransferCommand(event.getTransferId()));
                    }
                });
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyWithdrawnEvent event) {
        commandGateway.send(new DepositMoneyCommand(targetAccount, event.getTransactionId(), event.getAmount()),
                LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyDepositedEvent event) {
        commandGateway.send(new CompleteMoneyTransferCommand(transferId),
                LoggingCallback.INSTANCE);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transferId")
    public void on(MoneyTransferCompletedEvent event) {
    }

    @SagaEventHandler(associationProperty = "transferId")
    public void on(MoneyTransferCancelledEvent event) {

        end();
    }

}

