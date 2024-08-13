package uz.uzum.financeapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.uzum.financeapp.dto.IncomeDto;
import uz.uzum.financeapp.model.Income;
import uz.uzum.financeapp.repository.IncomeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public List<IncomeDto> getAllIncomes() {
        return incomeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<IncomeDto> getIncomeById(Long id) {
        return incomeRepository.findById(id)
                .map(this::convertToDTO);
    }

    public IncomeDto addIncome(IncomeDto IncomeDto) {
        Income income = convertToEntity(IncomeDto);
        Income savedIncome = incomeRepository.save(income);
        return convertToDTO(savedIncome);
    }

    public IncomeDto updateIncome(Long id, IncomeDto IncomeDto) {
        Income existingIncome = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found with id " + id));

        existingIncome.setAmount(IncomeDto.getAmount());
        existingIncome.setDescription(IncomeDto.getDescription());
        existingIncome.setDate(IncomeDto.getDate());

        Income updatedIncome = incomeRepository.save(existingIncome);
        return convertToDTO(updatedIncome);
    }

    public void deleteIncome(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found with id " + id));
        incomeRepository.delete(income);
    }

    public List<IncomeDto> getIncomesBetweenDates(LocalDate startDate, LocalDate endDate) {
        return incomeRepository.findAll().stream()
                .filter(income -> !income.getDate().isBefore(startDate) && !income.getDate().isAfter(endDate))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BigDecimal calculateTotalIncomeBetweenDates(LocalDate startDate, LocalDate endDate) {
        return incomeRepository.findAll().stream()
                .filter(income -> !income.getDate().isBefore(startDate) && !income.getDate().isAfter(endDate))
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Income convertToEntity(IncomeDto IncomeDto) {
        return new Income(IncomeDto.getAmount(), IncomeDto.getDescription(), IncomeDto.getDate());
    }

    private IncomeDto convertToDTO(Income income) {
        return new IncomeDto(income.getAmount(), income.getDescription(), income.getDate());
    }
}
