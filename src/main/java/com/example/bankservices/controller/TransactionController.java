package com.example.bankservices.controller;

import com.example.bankservices.dto.TransactionDTO;
import com.example.bankservices.dto.TransactionMapper;
import com.example.bankservices.model.Transaction;
import com.example.bankservices.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }



    @PostMapping
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO){
        transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
