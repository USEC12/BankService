package com.example.bankservices.dto;

import com.example.bankservices.model.CurrencyRate;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-22T17:00:21+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class CurrencyRateMapperImpl implements CurrencyRateMapper {

    @Override
    public CurrencyRateDTO toDto(CurrencyRate currencyRate) {
        if ( currencyRate == null ) {
            return null;
        }

        BigDecimal rate = null;

        CurrencyRateDTO currencyRateDTO = new CurrencyRateDTO( rate );

        return currencyRateDTO;
    }

    @Override
    public CurrencyRate fromDto(CurrencyRateDTO currencyRateDTO) {
        if ( currencyRateDTO == null ) {
            return null;
        }

        CurrencyRate currencyRate = new CurrencyRate();

        return currencyRate;
    }
}
