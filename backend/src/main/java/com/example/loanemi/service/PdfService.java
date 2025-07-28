package com.example.loanemi.service;

import com.example.loanemi.model.AmortizationEntry;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public byte[] generateAmortizationPdf(List<AmortizationEntry> schedule, double principal, double interestRate, int tenure, double emi) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        document.add(new Paragraph("Loan Amortization Schedule"));
        document.add(new Paragraph("Principal: " + String.format("%.2f", principal)));
        document.add(new Paragraph("Interest Rate: " + String.format("%.2f", interestRate) + "%"));
        document.add(new Paragraph("Tenure: " + tenure + " months"));
        document.add(new Paragraph("EMI: " + String.format("%.2f", emi)));
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(6);
        table.addCell("Month");
        table.addCell("Starting Balance");
        table.addCell("Monthly Payment");
        table.addCell("Interest Paid");
        table.addCell("Principal Paid");
        table.addCell("Ending Balance");

        for (AmortizationEntry entry : schedule) {
            table.addCell(String.valueOf(entry.getMonthNumber()));
            table.addCell(String.format("%.2f", entry.getStartingBalance()));
            table.addCell(String.format("%.2f", entry.getMonthlyPayment()));
            table.addCell(String.format("%.2f", entry.getInterestPaid()));
            table.addCell(String.format("%.2f", entry.getPrincipalPaid()));
            table.addCell(String.format("%.2f", entry.getEndingBalance()));
        }

        document.add(table);
        document.close();

        return baos.toByteArray();
    }
}
