// src/main/java/com/example/expensemanager/service/TransactionService.java
package uz.uzum.financeapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransactionService {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    @Autowired
    public TransactionService(IncomeService incomeService, ExpenseService expenseService) {
        this.incomeService = incomeService;
        this.expenseService = expenseService;
    }

    public BigDecimal calculateNetBalanceBetweenDates(LocalDate startDate, LocalDate endDate) {
        BigDecimal totalIncome = incomeService.calculateTotalIncomeBetweenDates(startDate, endDate);
        BigDecimal totalExpenses = expenseService.calculateTotalExpensesBetweenDates(startDate, endDate);
        return totalIncome.add(totalExpenses);
    }
}
