package com.example.bankservices.config;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import com.example.bankservices.dto.TransactionMapper;

@TestConfiguration
public class MapperTestConfig {

    @Bean
    public TransactionMapper transactionMapper() {
        return Mappers.getMapper(TransactionMapper.class);
    }
}
