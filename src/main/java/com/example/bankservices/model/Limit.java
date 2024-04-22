package com.example.bankservices.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "limits")
@Getter
@Setter
@NoArgsConstructor
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "limit_sum", nullable = false)
    private BigDecimal limitSum;
    @Column(name = "limit_date_time", nullable = false)
    private LocalDateTime limitDateTime;
    @Column(name = "limit_currency_shortname", nullable = false)
    private String limitCurrencyShortName;

}
