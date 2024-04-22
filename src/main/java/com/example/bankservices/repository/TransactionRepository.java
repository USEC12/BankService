package com.example.bankservices.repository;

import com.example.bankservices.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByLimitExceededTrue();//возвращает список транзакций, которых флаг "limitExceeded" true
}
