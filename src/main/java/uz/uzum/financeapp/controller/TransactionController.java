package uz.uzum.financeapp.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.uzum.financeapp.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/transactions")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Transactions", description = "Operations related with net balance and after tax calc")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/net-balance")
    public ResponseEntity<BigDecimal> getNetBalance(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        BigDecimal netBalance = transactionService.calculateNetBalance(startDate, endDate);
        return ResponseEntity.ok(netBalance);
    }

    @GetMapping("/after-tax-profit")
    public ResponseEntity<BigDecimal> getAfterTaxProfit(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("taxRate") BigDecimal taxRate) {

        BigDecimal afterTaxProfit = transactionService.getAfterTaxProfit(startDate, endDate, taxRate);
        return ResponseEntity.ok(afterTaxProfit);
    }

    @GetMapping("/tax-amount")
    public ResponseEntity<BigDecimal> getTaxAmount(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("taxRate") BigDecimal taxRate) {

        BigDecimal taxAmount = transactionService.getTaxAmount(startDate, endDate, taxRate);
        return ResponseEntity.ok(taxAmount);
    }

}
