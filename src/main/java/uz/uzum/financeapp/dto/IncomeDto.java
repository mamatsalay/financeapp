package uz.uzum.financeapp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDto {

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date;
}