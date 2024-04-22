package com.example.bankservices.model;

import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountFrom;
    private String accountTo;
    private String currencyShortName;
    private  BigDecimal sum;
    private String expenseCategory;
    private LocalDateTime dateTime;
    private boolean limitExceeded;

}
