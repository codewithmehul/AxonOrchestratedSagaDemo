package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class TransactionHistory {
    @Id
    @GeneratedValue
    private Long id;

    private String accountId;
    private String transactionId;
    private Integer amount;

    public TransactionHistory(String accountId, String transactionId, Integer amount) {
        this.accountId = accountId;
        this.transactionId = transactionId;
        this.amount = amount;
    }

    public TransactionHistory(String accountId) {
        this.accountId = accountId;
    }
}
