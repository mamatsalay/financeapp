package uz.uzum.financeapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.uzum.financeapp.dto.ExpenseDto;
import uz.uzum.financeapp.model.Expense;
import uz.uzum.financeapp.service.ExpenseService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExpenseControllerTest {

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseController expenseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateExpense() {
        // Arrange
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setAmount(BigDecimal.valueOf(50));
        expenseDto.setDescription("Groceries");
        expenseDto.setDate(LocalDate.now());

        Expense expense = new Expense();
        expense.setAmount(BigDecimal.valueOf(50));
        expense.setDescription("Groceries");
        expense.setDate(LocalDate.now());

        when(expenseService.createExpense(any(ExpenseDto.class))).thenReturn(expense);

        // Act
        ResponseEntity<Expense> response = expenseController.createExpense(expenseDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expense.getAmount(), response.getBody().getAmount());
        verify(expenseService, times(1)).createExpense(any(ExpenseDto.class));
    }

    @Test
    void testUpdateExpense() {
        // Arrange
        Long id = 1L;
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setAmount(BigDecimal.valueOf(75));
        expenseDto.setDescription("Updated Groceries");
        expenseDto.setDate(LocalDate.now());

        Expense updatedExpense = new Expense();
        updatedExpense.setId(id);
        updatedExpense.setAmount(BigDecimal.valueOf(75));
        updatedExpense.setDescription("Updated Groceries");
        updatedExpense.setDate(LocalDate.now());

        when(expenseService.updateExpense(anyLong(), any(ExpenseDto.class))).thenReturn(updatedExpense);

        // Act
        ResponseEntity<Expense> response = expenseController.updateExpense(id, expenseDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedExpense.getAmount(), response.getBody().getAmount());
        verify(expenseService, times(1)).updateExpense(anyLong(), any(ExpenseDto.class));
    }

    @Test
    void testDeleteExpense() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> response = expenseController.deleteExpense(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(expenseService, times(1)).deleteExpense(id);
    }

    @Test
    void testGetExpensesByDateRange() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        List<Expense> expenses = new ArrayList<>();
        Expense expense1 = new Expense();
        expense1.setAmount(BigDecimal.valueOf(50));
        expense1.setDescription("Groceries");
        expense1.setDate(LocalDate.of(2024, 2, 1));
        expenses.add(expense1);

        Expense expense2 = new Expense();
        expense2.setAmount(BigDecimal.valueOf(75));
        expense2.setDescription("Utilities");
        expense2.setDate(LocalDate.of(2024, 5, 1));
        expenses.add(expense2);

        when(expenseService.getExpensesByDateRange(startDate, endDate)).thenReturn(expenses);

        // Act
        ResponseEntity<List<Expense>> response = expenseController.getExpensesByDateRange(startDate, endDate);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(expenseService, times(1)).getExpensesByDateRange(startDate, endDate);
    }

}
