package uz.uzum.financeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzum.financeapp.model.Income;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link Income}
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDto {

    private BigDecimal amount;
    private String description;
    private LocalDate date;
}