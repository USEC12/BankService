package com.example.bankservices.service;

import com.example.bankservices.dto.LimitDTO;
import com.example.bankservices.dto.TransactionDTO;
import com.example.bankservices.dto.TransactionMapper;
import com.example.bankservices.model.Limit;
import com.example.bankservices.model.Transaction;
import com.example.bankservices.repository.LimitRepository;
import com.example.bankservices.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final LimitRepository limitRepository;

    private final TransactionRepository transactionRepository;

    @Autowired
    public ClientService(TransactionRepository transactionRepository, LimitRepository limitRepository) {
        this.transactionRepository = transactionRepository;
        this.limitRepository = limitRepository;
    }

    public void setLimit(LimitDTO limitDTO) {
        if (limitDTO.getLimitDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot set future limit dates.");
        }
        Limit limit = new Limit();
        limit.setLimitSum(limitDTO.getLimitSum());
        limit.setLimitDateTime(limitDTO.getLimitDateTime());
        limit.setLimitCurrencyShortName(limitDTO.getLimitCurrencyShortName());
        limitRepository.save(limit);
    }

    public List<TransactionDTO> getTransactionsExceededLimits() {
        List<Transaction> transactions = transactionRepository.findByLimitExceededTrue();
        return transactions.stream().map(this::mapTransactionToDto).collect(Collectors.toList());
    }

    private TransactionDTO mapTransactionToDto(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setAccountFrom(transaction.getAccountFrom());
        dto.setAccountTo(transaction.getAccountTo());
        dto.setCurrencyShortName(transaction.getCurrencyShortName());
        dto.setSum(transaction.getSum());
        dto.setExpenseCategory(transaction.getExpenseCategory());
        dto.setDateTime(transaction.getDateTime());
        return TransactionMapper.INSTANCE.transactionToTransactionDTO(transaction);
    }
}


