package com.example.bankservices.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LimitDTO {
    private Long id;
    private BigDecimal limitSum;
    private LocalDateTime limitDateTime;
    private String limitCurrencyShortName;
}
