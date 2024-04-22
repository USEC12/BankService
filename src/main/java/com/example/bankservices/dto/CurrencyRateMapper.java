package com.example.bankservices.dto;

import com.example.bankservices.model.CurrencyRate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CurrencyRateMapper {
    CurrencyRateMapper INSTANCE = Mappers.getMapper(CurrencyRateMapper.class);

    CurrencyRateDTO toDto(CurrencyRate currencyRate);
    CurrencyRate fromDto(CurrencyRateDTO currencyRateDTO);
}