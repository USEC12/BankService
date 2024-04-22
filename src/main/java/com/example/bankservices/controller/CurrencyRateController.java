package com.example.bankservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.bankservices.dto.CurrencyRateDTO;
import com.example.bankservices.service.CurrencyRateService;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/currency-rates")
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    @Autowired
    public CurrencyRateController(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }




    @GetMapping("/{currencyCode}")
    public ResponseEntity<?> getCurrencyRate(@PathVariable String currencyCode) {
        try {
            CurrencyRateDTO currencyRate = currencyRateService.getRate(currencyCode, LocalDate.now());
            if (currencyRate != null) {
                return ResponseEntity.ok(currencyRate);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving currency rate: " + e.getMessage());
        }
    }


    @PostMapping("/update")
    public ResponseEntity<?> updateCurrencyRates() {
        try {
            currencyRateService.updateRates();
            return ResponseEntity.ok("Currency rates updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update currency rates: " + e.getMessage());
        }
    }
}
