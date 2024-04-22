package com.example.bankservices.model;

import com.example.bankservices.dto.TransactionDTO;
import com.example.bankservices.repository.TransactionRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import java.time.LocalDateTime;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;  // Правильный импорт
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void whenPostTransactionAndLimitExceeded_thenBadRequest() throws Exception {
        // Допустим, у нас уже есть лимит в базе данных
        TransactionDTO dto = new TransactionDTO();
        dto.setSum(new BigDecimal("1200")); // Выше лимита
        dto.setDateTime(LocalDateTime.now());

        String json = objectMapper.writeValueAsString(dto);

        // Выполнение запроса с использованием правильного метода post
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest()); // Предполагаем, что контроллер возвращает BadRequest, если лимит превышен
    }

    }

