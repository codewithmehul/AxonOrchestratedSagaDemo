package com.example.repository;

import com.example.dto.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBalanceRepository extends CassandraRepository<AccountBalance, String> {
}
