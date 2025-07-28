package com.example.loanemi.service;

import com.example.loanemi.model.AmortizationEntry;
import com.example.loanemi.model.LoanCalculation;
import com.example.loanemi.repository.LoanCalculationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanCalculationRepository repository;

    public LoanCalculation calculateEmi(LoanCalculation calculation) {
        double principal = calculation.getPrincipal();
        double interestRate = calculation.getInterestRate();
        int tenure = calculation.getTenure();

        double monthlyInterestRate = interestRate / 100 / 12;
        double emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenure))
                / (Math.pow(1 + monthlyInterestRate, tenure) - 1);

        calculation.setEmi(emi);
        return repository.save(calculation);
    }

    public List<LoanCalculation> getHistory() {
        return repository.findAll();
    }

    public List<AmortizationEntry> generateAmortizationSchedule(double principal, double annualInterestRate, int tenureInMonths) {
        List<AmortizationEntry> schedule = new ArrayList<>();
        double monthlyInterestRate = annualInterestRate / 100 / 12;
        double emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenureInMonths))
                / (Math.pow(1 + monthlyInterestRate, tenureInMonths) - 1);

        double remainingBalance = principal;

        for (int month = 1; month <= tenureInMonths; month++) {
            AmortizationEntry entry = new AmortizationEntry();
            entry.setMonthNumber(month);
            entry.setStartingBalance(remainingBalance);

            double interestPaid = remainingBalance * monthlyInterestRate;
            double principalPaid = emi - interestPaid;
            remainingBalance -= principalPaid;

            entry.setMonthlyPayment(emi);
            entry.setInterestPaid(interestPaid);
            entry.setPrincipalPaid(principalPaid);
            entry.setEndingBalance(remainingBalance > 0 ? remainingBalance : 0); // Ensure balance doesn't go negative due to rounding

            schedule.add(entry);
        }
        return schedule;
    }
}
