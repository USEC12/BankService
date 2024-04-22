package com.example.bankservices.dto;

import com.example.bankservices.model.Limit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LimitMapper {
    LimitMapper INSTANCE = Mappers.getMapper(LimitMapper.class);

    LimitDTO toDto(Limit limit);
    Limit toEntity(LimitDTO limitDTO);
}