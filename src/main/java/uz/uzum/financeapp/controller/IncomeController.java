package uz.uzum.financeapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzum.financeapp.dto.IncomeDto;
import uz.uzum.financeapp.model.Income;
import uz.uzum.financeapp.service.IncomeService;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Income> createIncome(@RequestBody IncomeDto incomeDto) {
        Income income = incomeService.createIncome(incomeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(income);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Income> updateIncome(@PathVariable Long id, @RequestBody IncomeDto incomeDTO) {
        Income updatedIncome = incomeService.updateIncome(id, incomeDTO);
        return ResponseEntity.ok(updatedIncome);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }

}
