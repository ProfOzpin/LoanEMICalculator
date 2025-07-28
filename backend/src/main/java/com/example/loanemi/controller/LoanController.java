package com.example.loanemi.controller;

import com.example.loanemi.model.AmortizationEntry;
import com.example.loanemi.model.LoanCalculation;
import com.example.loanemi.service.LoanService;
import com.example.loanemi.service.PdfService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    @Autowired
    private LoanService service;

    @Autowired
    private PdfService pdfService;

    @PostMapping("/calculate")
    public LoanCalculation calculateEmi(@RequestBody LoanCalculation calculation) {
        return service.calculateEmi(calculation);
    }

    @GetMapping("/history")
    public List<LoanCalculation> getHistory() {
        return service.getHistory();
    }

    @GetMapping("/amortization")
    public List<AmortizationEntry> getAmortizationSchedule(@RequestParam double principal, @RequestParam double interestRate, @RequestParam int tenure) {
        return service.generateAmortizationSchedule(principal, interestRate, tenure);
    }

    @GetMapping("/amortization/pdf")
    public ResponseEntity<byte[]> getAmortizationPdf(@RequestParam double principal, @RequestParam double interestRate, @RequestParam int tenure) throws DocumentException, IOException {
        List<AmortizationEntry> schedule = service.generateAmortizationSchedule(principal, interestRate, tenure);
        double emi = service.calculateEmi(new LoanCalculation(principal, interestRate, tenure)).getEmi(); // Recalculate EMI for PDF header
        byte[] pdfBytes = pdfService.generateAmortizationPdf(schedule, principal, interestRate, tenure, emi);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "amortization_schedule.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
