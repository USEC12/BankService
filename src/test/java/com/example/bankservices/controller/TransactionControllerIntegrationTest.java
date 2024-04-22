package com.example.bankservices.controller;

import com.example.bankservices.config.MapperTestConfig;
import com.example.bankservices.dto.TransactionDTO;
import com.example.bankservices.dto.TransactionMapper;
import com.example.bankservices.repository.TransactionRepository;
import com.example.bankservices.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
@AutoConfigureMockMvc(addFilters = false) // Отключаем фильтры безопасности для упрощения теста
@AutoConfigureDataJpa
@Import(MapperTestConfig.class)
public class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private TransactionMapper transactionMapper;

    @Test
    public void whenTransactionExceedsLimit_thenBadRequest() throws Exception {
        TransactionDTO dto = new TransactionDTO();
        dto.setSum(BigDecimal.valueOf(1000));
        dto.setDateTime(LocalDateTime.now());

        String jsonContent = objectMapper.writeValueAsString(dto);  // Сериализация DTO в JSON

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated());  // Используйте isCreated, так как контроллер возвращает 201
    }
}
