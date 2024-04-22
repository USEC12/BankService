package com.example.bankservices.controller;

import com.example.bankservices.dto.LimitDTO;
import com.example.bankservices.dto.TransactionDTO;
import com.example.bankservices.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/limits")
    public ResponseEntity<Void> setLimit(@RequestBody LimitDTO limitDTO) {
        clientService.setLimit(limitDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/transactions/exceeded")
    public ResponseEntity<List<TransactionDTO>> getTransactionsExceededLimits() {
        List<TransactionDTO> exceededTransactions = clientService.getTransactionsExceededLimits();
        return ResponseEntity.ok(exceededTransactions);
    }
}