package com.example.query;

import com.example.dto.TransactionHistory;
import com.example.events.MoneyDepositedEvent;
import com.example.events.MoneyWithdrawnEvent;
import com.example.repository.TransactionHistoryRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionHistoryEventHandler {
    private final TransactionHistoryRepository repository;

    public TransactionHistoryEventHandler(TransactionHistoryRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(MoneyDepositedEvent event) {
        repository.save(new TransactionHistory(event.getAccountId(), event.getTransactionId(), event.getAmount()));
    }

    @EventHandler
    public void on(MoneyWithdrawnEvent event) {
        repository.save(new TransactionHistory(event.getAccountId(), event.getTransactionId(), event.getAmount()));
    }

    @GetMapping("/history/{accountId}")
    public List<TransactionHistory> findTransactions(@PathVariable String accountId) {
        return repository.findAll(Example.of(new TransactionHistory(accountId)));
    }
}
