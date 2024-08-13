package uz.uzum.financeapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.uzum.financeapp.model.Expense;
import uz.uzum.financeapp.model.ExpenseDto;
import uz.uzum.financeapp.repository.ExpenseRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<ExpenseDto> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ExpenseDto> getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ExpenseDto addExpense(ExpenseDto ExpenseDto) {
        Expense expense = convertToEntity(ExpenseDto);
        Expense savedExpense = expenseRepository.save(expense);
        return convertToDTO(savedExpense);
    }

    public ExpenseDto updateExpense(Long id, ExpenseDto ExpenseDto) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));

        existingExpense.setAmount(ExpenseDto.getAmount());
        existingExpense.setDescription(ExpenseDto.getDescription());
        existingExpense.setDate(ExpenseDto.getDate());

        Expense updatedExpense = expenseRepository.save(existingExpense);
        return convertToDTO(updatedExpense);
    }

    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
        expenseRepository.delete(expense);
    }

    public List<ExpenseDto> getExpensesBetweenDates(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findAll().stream()
                .filter(expense -> !expense.getDate().isBefore(startDate) && !expense.getDate().isAfter(endDate))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BigDecimal calculateTotalExpensesBetweenDates(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findAll().stream()
                .filter(expense -> !expense.getDate().isBefore(startDate) && !expense.getDate().isAfter(endDate))
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::subtract);
    }

    private ExpenseDto convertToDTO(Expense expense) {
        return new ExpenseDto(expense.getAmount(), expense.getDescription(), expense.getDate());
    }

    private Expense convertToEntity(ExpenseDto ExpenseDto) {
        return new Expense(ExpenseDto.getAmount(), ExpenseDto.getDescription(), ExpenseDto.getDate());
    }
}
