package com.example.bankservices.dto;


import com.example.bankservices.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;



@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    TransactionDTO transactionToTransactionDTO(Transaction transaction);
    TransactionDTO toDto(Transaction transaction);
    Transaction toEntity(TransactionDTO transactionDTO);
}
