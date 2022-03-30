package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

public class AccountBalance {
    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccountBalance {
        @Id
        private String accountId;
        private int balance;

    }
}
