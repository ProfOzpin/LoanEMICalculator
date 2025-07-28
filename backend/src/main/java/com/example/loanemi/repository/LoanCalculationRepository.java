package com.example.loanemi.repository;

import com.example.loanemi.model.LoanCalculation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanCalculationRepository extends JpaRepository<LoanCalculation, Long> {
}
