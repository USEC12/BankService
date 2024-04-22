package com.example.bankservices.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    private String accountFrom;
    private String accountTo;
    private String currencyShortName;
    private BigDecimal sum;
    private String expenseCategory;
    private LocalDateTime dateTime;


}
