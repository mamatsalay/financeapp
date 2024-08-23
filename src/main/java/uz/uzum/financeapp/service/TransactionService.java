package uz.uzum.financeapp.service;


import org.springframework.stereotype.Service;
import uz.uzum.financeapp.repository.ExpenseRepository;
import uz.uzum.financeapp.repository.IncomeRepository;
import uz.uzum.financeapp.security.SecurityUtil;
import uz.uzum.financeapp.model.Income;
import uz.uzum.financeapp.model.Expense;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class TransactionService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final SecurityUtil securityUtil;

    public TransactionService(IncomeRepository incomeRepository, ExpenseRepository expenseRepository, SecurityUtil securityUtil) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.securityUtil = securityUtil;
    }

    public BigDecimal calculateNetBalance(LocalDate startDate, LocalDate endDate) {
        String username = securityUtil.getUsernameFromSecurityContext();

        // Calculate total incomes within the date range
        BigDecimal totalIncome = incomeRepository.findIncomesByDateRangeAndUser(username, startDate, endDate)
                .stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate total expenses within the date range
        BigDecimal totalExpense = expenseRepository.findExpensesByDateRangeAndUser(username, startDate, endDate)
                .stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Net balance is income minus expenses
        return totalIncome.subtract(totalExpense).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateAfterTaxProfit(BigDecimal netBalance, BigDecimal taxRate) {
        BigDecimal taxAmount = netBalance.multiply(taxRate);
        return netBalance.subtract(taxAmount).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getAfterTaxProfit(LocalDate startDate, LocalDate endDate, BigDecimal taxRate) {
        BigDecimal netBalance = calculateNetBalance(startDate, endDate);
        return calculateAfterTaxProfit(netBalance, taxRate);
    }

    public BigDecimal calculateTaxAmount(BigDecimal netBalance, BigDecimal taxRate) {
        return netBalance.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTaxAmount(LocalDate startDate, LocalDate endDate, BigDecimal taxRate) {
        BigDecimal netBalance = calculateNetBalance(startDate, endDate);
        return calculateTaxAmount(netBalance, taxRate);
    }

}
