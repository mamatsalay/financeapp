package uz.uzum.financeapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.uzum.financeapp.dto.IncomeDto;
import uz.uzum.financeapp.model.Income;
import uz.uzum.financeapp.service.IncomeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class IncomeControllerTest {

    @Mock
    private IncomeService incomeService;

    @InjectMocks
    private IncomeController incomeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateIncome() {
        // Arrange
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setAmount(BigDecimal.valueOf(150));
        incomeDto.setDescription("Salary");
        incomeDto.setDate(LocalDate.now());

        Income income = new Income();
        income.setAmount(BigDecimal.valueOf(150));
        income.setDescription("Salary");
        income.setDate(LocalDate.now());

        when(incomeService.createIncome(any(IncomeDto.class))).thenReturn(income);

        // Act
        ResponseEntity<Income> response = incomeController.createIncome(incomeDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(income.getAmount(), response.getBody().getAmount());
        verify(incomeService, times(1)).createIncome(any(IncomeDto.class));
    }

    @Test
    void testUpdateIncome() {
        // Arrange
        Long id = 1L;
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setAmount(BigDecimal.valueOf(200));
        incomeDto.setDescription("Updated Salary");
        incomeDto.setDate(LocalDate.now());

        Income updatedIncome = new Income();
        updatedIncome.setId(id);
        updatedIncome.setAmount(BigDecimal.valueOf(200));
        updatedIncome.setDescription("Updated Salary");
        updatedIncome.setDate(LocalDate.now());

        when(incomeService.updateIncome(anyLong(), any(IncomeDto.class))).thenReturn(updatedIncome);

        // Act
        ResponseEntity<Income> response = incomeController.updateIncome(id, incomeDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedIncome.getAmount(), response.getBody().getAmount());
        verify(incomeService, times(1)).updateIncome(anyLong(), any(IncomeDto.class));
    }

    @Test
    void testDeleteIncome() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> response = incomeController.deleteIncome(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(incomeService, times(1)).deleteIncome(id);
    }

    @Test
    void testGetIncomesByDateRange() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        List<Income> incomes = new ArrayList<>();
        Income income1 = new Income();
        income1.setAmount(BigDecimal.valueOf(150));
        income1.setDescription("Salary");
        income1.setDate(LocalDate.of(2024, 2, 1));
        incomes.add(income1);

        Income income2 = new Income();
        income2.setAmount(BigDecimal.valueOf(200));
        income2.setDescription("Bonus");
        income2.setDate(LocalDate.of(2024, 5, 1));
        incomes.add(income2);

        when(incomeService.getIncomesByDateRange(startDate, endDate)).thenReturn(incomes);

        // Act
        ResponseEntity<List<Income>> response = incomeController.getIncomesByDateRange(startDate, endDate);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(incomeService, times(1)).getIncomesByDateRange(startDate, endDate);
    }

}
