package com.example.bankservices.service;

import com.example.bankservices.dto.CurrencyRateDTO;
import com.example.bankservices.dto.CurrencyRateMapper;
import com.example.bankservices.model.CurrencyRate;
import com.example.bankservices.repository.CurrencyRateRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class CurrencyRateService {
    private final CurrencyRateRepository currencyRateRepository;
    private final WebClient webClient;
    private final CurrencyRateMapper currencyRateMapper;
    private static final Logger logger = LoggerFactory.getLogger(CurrencyRateService.class);
    @Autowired
    public CurrencyRateService(CurrencyRateRepository currencyRateRepository, WebClient.Builder webClientBuilder, CurrencyRateMapper currencyRateMapper) {
        this.currencyRateRepository = currencyRateRepository;
        this.webClient = webClientBuilder.baseUrl("https://www.alphavantage.co").build();
        this.currencyRateMapper = currencyRateMapper;
    }

    public void updateRates() {
        updateCurrencyRate("KZT", "USD");
        updateCurrencyRate("RUB", "USD");
    }
    private void updateCurrencyRate(String fromCurrency, String toCurrency) {
        String apiKey = "ZBQAJ8EZ0NNVTV9V";
        String uri = String.format("/query?function=CURRENCY_EXCHANGE_RATE&from_currency=%s&to_currency=%s&apikey=%s", fromCurrency, toCurrency, apiKey);
        try {
            CurrencyRateDTO currencyRateDTO = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(CurrencyRateDTO.class)
                    .block();
            if (currencyRateDTO != null) {
                CurrencyRate rate = currencyRateMapper.fromDto(currencyRateDTO);
                rate.setRateDate(LocalDate.now()); // Assuming the current date for the rate date
                currencyRateRepository.save(rate);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update currency rates for " + fromCurrency + " to " + toCurrency);
        }
    }
    public CurrencyRateDTO getRate(String sourceCurrency, LocalDate date) {
        logger.info("Fetching rate for currency: " + sourceCurrency + " on date: " + date);
        if (sourceCurrency == null) {
            logger.error("Source currency is null");
            throw new IllegalArgumentException("Source currency must not be null");
        }
        return currencyRateRepository.findBySourceCurrencyAndRateDate(sourceCurrency, date)
                .map(currencyRateMapper::toDto)
                .orElseGet(() -> fetchAndSaveRate(sourceCurrency, date));
    }

    private CurrencyRateDTO fetchAndSaveRate(String sourceCurrency, LocalDate date) {
        String uri = String.format("/query?function=CURRENCY_EXCHANGE_RATE&from_currency=%s&to_currency=USD&apikey=ZBQAJ8EZ0NNVTV9V", sourceCurrency);
        JsonNode responseNode = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (responseNode != null && responseNode.has("Realtime Currency Exchange Rate")) {
            JsonNode rateData = responseNode.get("Realtime Currency Exchange Rate");
            CurrencyRate rate = new CurrencyRate();
            rate.setSourceCurrency(sourceCurrency);
            rate.setTargetCurrency("USD");
            rate.setRate(new BigDecimal(rateData.get("5. Exchange Rate").asText()));
            rate.setRateDate(date);  // Может потребоваться обработка даты
            currencyRateRepository.save(rate);
            return currencyRateMapper.toDto(rate);
        }
        throw new RuntimeException("Unable to fetch rate for " + sourceCurrency);
    }
}
