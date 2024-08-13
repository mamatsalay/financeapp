// src/main/java/com/example/expensemanager/controller/TransactionController.java
package uz.uzum.financeapp.controller;

import uz.uzum.financeapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/net-balance")
    public BigDecimal getNetBalance(@RequestParam("start") String startDate,
                                    @RequestParam("end") String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return transactionService.calculateNetBalanceBetweenDates(start, end);
    }
}
