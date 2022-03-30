package com.example.repository;

import com.example.dto.AccountBalance;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountBalanceRepository extends CassandraRepository<AccountBalance, String> {
    @AllowFiltering
    List<AccountBalance> findByAccountId(String account_id);
}
