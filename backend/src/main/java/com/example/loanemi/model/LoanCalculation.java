package com.example.loanemi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class LoanCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double principal;
    private double interestRate;
    private int tenure;
    private double emi;

    public LoanCalculation(double principal, double interestRate, int tenure) {
        this.principal = principal;
        this.interestRate = interestRate;
        this.tenure = tenure;
    }

    public LoanCalculation() {
    }
}
