package uz.uzum.financeapp.service;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import uz.uzum.financeapp.dto.ExpenseDto;
import uz.uzum.financeapp.exception.InvalidDateRangeException;
import uz.uzum.financeapp.exception.ResourceNotFoundException;
import uz.uzum.financeapp.model.Expense;
import uz.uzum.financeapp.model.UserInfo;
import uz.uzum.financeapp.repository.ExpenseRepository;
import uz.uzum.financeapp.repository.UserInfoRepository;
import uz.uzum.financeapp.security.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final SecurityUtil securityUtil;

    public ExpenseService(ExpenseRepository expenseRepository, SecurityUtil securityUtil) {
        this.expenseRepository = expenseRepository;
        this.securityUtil = securityUtil;
    }

    public Expense createExpense(ExpenseDto expenseDto) {
        UserInfo userInfo = securityUtil.getAuthenticatedUser();
        Expense expense = new Expense(userInfo, expenseDto.getAmount(), expenseDto.getDescription(), expenseDto.getDate());
        return expenseRepository.save(expense);
    }

        public Expense updateExpense(Long id ,ExpenseDto expenseDto) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not Found"));

        expense.setAmount(expenseDto.getAmount());
        expense.setDescription(expenseDto.getDescription());
        expense.setDate(expenseDto.getDate());
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not Found"));

        if(!securityUtil.isAdmin() && !expense.getUserInfo().getUsername().equals(securityUtil.getUsernameFromSecurityContext())){
            throw new AccessDeniedException("You do not have permission to delete this expense");
        }

        expenseRepository.delete(expense);
    }

    public List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {

        if (endDate.isBefore(startDate)) {
            throw new InvalidDateRangeException("End date cannot be before start date.");
        }

        String username = securityUtil.getUsernameFromSecurityContext();
        return expenseRepository.findExpensesByDateRangeAndUser(username, startDate, endDate);
    }
}

