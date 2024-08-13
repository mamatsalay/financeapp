package uz.uzum.financeapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzum.financeapp.dto.IncomeDto;
import uz.uzum.financeapp.service.IncomeService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping
    public List<IncomeDto> getAllIncomes() {
        return incomeService.getAllIncomes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeDto> getIncomeById(@PathVariable Long id) {
        return incomeService.getIncomeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/between")
    public List<IncomeDto> getIncomesBetweenDates(@RequestParam("start") String startDate,
                                                  @RequestParam("end") String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return incomeService.getIncomesBetweenDates(start, end);
    }

    @PostMapping
    public IncomeDto addIncome(@RequestBody IncomeDto IncomeDto) {
        return incomeService.addIncome(IncomeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeDto> updateIncome(
            @PathVariable Long id, @RequestBody IncomeDto IncomeDto) {
        return ResponseEntity.ok(incomeService.updateIncome(id, IncomeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }
}
