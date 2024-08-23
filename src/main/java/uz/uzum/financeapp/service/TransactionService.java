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

    /**
     * Calculate the after-tax profit based on the net balance.
     *
     * @param netBalance the net balance before tax
     * @param taxRate    the tax rate to apply (e.g., 0.2 for 20%)
     * @return the after-tax profit as BigDecimal
     */
    public BigDecimal calculateAfterTaxProfit(BigDecimal netBalance, BigDecimal taxRate) {
        BigDecimal taxAmount = netBalance.multiply(taxRate);
        return netBalance.subtract(taxAmount).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Get the after-tax profit for a user within the specified date range.
     *
     * @param startDate the start date of the range
     * @param endDate   the end date of the range
     * @param taxRate   the tax rate to apply (e.g., 0.2 for 20%)
     * @return the after-tax profit as BigDecimal
     */
    public BigDecimal getAfterTaxProfit(LocalDate startDate, LocalDate endDate, BigDecimal taxRate) {
        BigDecimal netBalance = calculateNetBalance(startDate, endDate);
        return calculateAfterTaxProfit(netBalance, taxRate);
    }

}
