package uz.uzum.financeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link Expense}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDto {

    private BigDecimal amount;
    private String description;
    private LocalDate date;
}