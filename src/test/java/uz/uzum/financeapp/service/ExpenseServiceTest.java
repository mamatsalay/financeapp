package uz.uzum.financeapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.uzum.financeapp.dto.ExpenseDto;
import uz.uzum.financeapp.model.Expense;
import uz.uzum.financeapp.model.UserInfo;
import uz.uzum.financeapp.repository.ExpenseRepository;
import uz.uzum.financeapp.repository.UserInfoRepository;
import uz.uzum.financeapp.security.SecurityUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private ExpenseService expenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateExpense() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("testUser");

        when(securityUtil.getUsernameFromSecurityContext()).thenReturn("testUser");
        when(userInfoRepository.findByUsername("testUser")).thenReturn(Optional.of(userInfo));
        when(expenseRepository.save(any(Expense.class))).thenReturn(new Expense());

        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setAmount(BigDecimal.valueOf(50));
        expenseDto.setDescription("Groceries");
        expenseDto.setDate(LocalDate.now());

        Expense result = expenseService.createExpense(expenseDto);

        assertNotNull(result);
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void testUpdateExpense() {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setAmount(BigDecimal.valueOf(50));

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setAmount(BigDecimal.valueOf(75));
        expenseDto.setDescription("Updated Groceries");
        expenseDto.setDate(LocalDate.now());

        Expense result = expenseService.updateExpense(1L, expenseDto);

        assertEquals(BigDecimal.valueOf(75), result.getAmount());
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void testDeleteExpense() {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setAmount(BigDecimal.valueOf(50));
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("testUser");
        expense.setUserInfo(userInfo);

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        when(securityUtil.getUsernameFromSecurityContext()).thenReturn("testUser");

        expenseService.deleteExpense(1L);

        verify(expenseRepository, times(1)).delete(any(Expense.class));
    }

}
