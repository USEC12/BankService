package com.example.bankservices.repository;

import com.example.bankservices.model.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface LimitRepository extends JpaRepository<Limit,Long> {
    @Query("SELECT l FROM Limit l WHERE l.limitDateTime <= :date ORDER BY l.limitDateTime DESC")
    Limit findLatestLimitBeforeDate(LocalDate date);
}
