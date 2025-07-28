package com.example.loanemi.model;

import lombok.Data;

@Data
public class AmortizationEntry {
    private int monthNumber;
    private double startingBalance;
    private double monthlyPayment;
    private double interestPaid;
    private double principalPaid;
    private double endingBalance;
}
