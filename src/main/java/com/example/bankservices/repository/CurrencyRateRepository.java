package com.example.bankservices.repository;

import com.example.bankservices.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    Optional<CurrencyRate> findBySourceCurrencyAndRateDate(String sourceCurrency, LocalDate date);

}
