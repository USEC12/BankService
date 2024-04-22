package com.example.bankservices.service;

import com.example.bankservices.model.Limit;
import com.example.bankservices.model.Transaction;
import com.example.bankservices.repository.LimitRepository;
import com.example.bankservices.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private LimitRepository limitRepository;

    @InjectMocks
    private TransactionService transactionService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void whenLimitExceeded_thenFlagIsSet() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setSum(new BigDecimal("1200"));
        transaction.setDateTime(LocalDateTime.now());

        Limit limit = new Limit();
        limit.setLimitSum(new BigDecimal("1000"));

        when(limitRepository.findLatestLimitBeforeDate(any())).thenReturn(limit);

        // Act
        transactionService.checkAndSetLimitExceeded(transaction);

        // Assert
        assertTrue(transaction.isLimitExceeded());
    }

    @Test
    void whenLimitNotExceeded_thenFlagIsNotSet() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setSum(new BigDecimal("800"));
        transaction.setDateTime(LocalDateTime.now());

        Limit limit = new Limit();
        limit.setLimitSum(new BigDecimal("1000"));

        when(limitRepository.findLatestLimitBeforeDate(any())).thenReturn(limit);

        // Act
        transactionService.checkAndSetLimitExceeded(transaction);

        // Assert
        assertFalse(transaction.isLimitExceeded());
    }


    @Test
    void whenLimitExceeded_thenFlagIsTrue() {
        Transaction transaction = new Transaction();
        transaction.setSum(BigDecimal.valueOf(1000));
        transaction.setDateTime(LocalDateTime.now());

        Limit limit = new Limit();
        limit.setLimitSum(BigDecimal.valueOf(500));  // Установленный лимит ниже суммы транзакции

        when(limitRepository.findLatestLimitBeforeDate(any())).thenReturn(limit);

        transactionService.checkAndSetLimitExceeded(transaction);

        assertTrue(transaction.isLimitExceeded());
    }

    @Test
    void whenLimitNotExceeded_thenFlagIsFalse() {
        Transaction transaction = new Transaction();
        transaction.setSum(BigDecimal.valueOf(300));
        transaction.setDateTime(LocalDateTime.now());

        Limit limit = new Limit();
        limit.setLimitSum(BigDecimal.valueOf(500));  // Установленный лимит выше суммы транзакции

        when(limitRepository.findLatestLimitBeforeDate(any())).thenReturn(limit);

        transactionService.checkAndSetLimitExceeded(transaction);

        assertFalse(transaction.isLimitExceeded());
    }
}