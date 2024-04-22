package com.example.bankservices.dto;

import com.example.bankservices.model.Limit;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-22T17:00:21+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class LimitMapperImpl implements LimitMapper {

    @Override
    public LimitDTO toDto(Limit limit) {
        if ( limit == null ) {
            return null;
        }

        LimitDTO limitDTO = new LimitDTO();

        return limitDTO;
    }

    @Override
    public Limit toEntity(LimitDTO limitDTO) {
        if ( limitDTO == null ) {
            return null;
        }

        Limit limit = new Limit();

        return limit;
    }
}
