package com.example.bankservices.service;

import com.example.bankservices.dto.CurrencyRateDTO;
import com.example.bankservices.dto.TransactionDTO;

import com.example.bankservices.model.Limit;
import com.example.bankservices.model.Transaction;
import com.example.bankservices.repository.LimitRepository;
import com.example.bankservices.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    private final CurrencyRateService currencyRateService;
    private final TransactionRepository transactionRepository;
    private final LimitRepository limitRepository;


    @Autowired
    public TransactionService(TransactionRepository transactionRepository, LimitRepository limitRepository,CurrencyRateService currencyRateService) {
        this.transactionRepository = transactionRepository;
        this.limitRepository = limitRepository;
        this.currencyRateService = currencyRateService;
    }


    public void createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = mapDtoToTransaction(transactionDTO);
        BigDecimal convertedAmount = convertToUSD(transaction.getSum(), transaction.getCurrencyShortName(), transaction.getDateTime());
        transaction.setSum(convertedAmount); // Store in USD for consistency
        checkAndSetLimitExceeded(transaction);
        transactionRepository.save(transaction);
    }

    private Transaction mapDtoToTransaction(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        transaction.setAccountFrom(dto.getAccountFrom());
        transaction.setAccountTo(dto.getAccountTo());
        transaction.setCurrencyShortName(dto.getCurrencyShortName());
        transaction.setSum(dto.getSum());
        transaction.setExpenseCategory(dto.getExpenseCategory());
        transaction.setDateTime(dto.getDateTime());
        return transaction;
    }
    public void checkAndSetLimitExceeded(Transaction transaction) {
        BigDecimal sumInUSD = transaction.getSum(); // Уже конвертировано в USD
        Limit currentLimit = limitRepository.findLatestLimitBeforeDate(transaction.getDateTime().toLocalDate());
        if (currentLimit != null && sumInUSD.compareTo(currentLimit.getLimitSum()) > 0) {
            transaction.setLimitExceeded(true);
        } else {
            transaction.setLimitExceeded(false);
        }
    }
    public BigDecimal convertToUSD(BigDecimal sum, String currency, LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        CurrencyRateDTO rateDTO = currencyRateService.getRate(currency, date);
        if (rateDTO == null || rateDTO.getRate() == null) {
            throw new IllegalStateException("Currency rate not available for " + currency + " on " + date);
        }
        return sum.multiply(rateDTO.getRate());
    }


}
