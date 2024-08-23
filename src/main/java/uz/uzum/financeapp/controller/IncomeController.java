package uz.uzum.financeapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzum.financeapp.dto.IncomeDto;
import uz.uzum.financeapp.model.Income;
import uz.uzum.financeapp.service.IncomeService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/incomes")
@SecurityRequirement(name = "Bearer Authentication")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping("/create")
    @Operation(summary = "Save a new record", description = "Creates a new income record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request, Invalid Input"),
    })
    public ResponseEntity<Income> createIncome(@Valid @RequestBody IncomeDto incomeDto) {
        Income income = incomeService.createIncome(incomeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(income);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update the existing record", description = "Update income record by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request, Invalid Input"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })
    public ResponseEntity<Income> updateIncome(@PathVariable Long id, @Valid @RequestBody IncomeDto incomeDTO) {
        Income updatedIncome = incomeService.updateIncome(id, incomeDTO);
        return ResponseEntity.ok(updatedIncome);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete the existing record", description = "Delete income record by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request, Invalid Input"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-date")
    @Operation(summary = "Shows list of records by date", description = "Show list of income records by date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request, Invalid Input"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })
    public ResponseEntity<List<Income>> getIncomesByDateRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<Income> incomes = incomeService.getIncomesByDateRange(startDate, endDate);
        return ResponseEntity.ok(incomes);
    }

}
