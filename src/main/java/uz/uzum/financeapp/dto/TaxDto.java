package uz.uzum.financeapp.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TaxDto {

    private LocalDate start;
    private LocalDate end;
    private BigDecimal taxRate;

}
