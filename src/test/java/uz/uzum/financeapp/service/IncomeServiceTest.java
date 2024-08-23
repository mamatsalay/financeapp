package uz.uzum.financeapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.uzum.financeapp.dto.IncomeDto;
import uz.uzum.financeapp.model.Income;
import uz.uzum.financeapp.model.UserInfo;
import uz.uzum.financeapp.repository.IncomeRepository;
import uz.uzum.financeapp.repository.UserInfoRepository;
import uz.uzum.financeapp.security.SecurityUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class IncomeServiceTest {

    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private IncomeService incomeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateIncome() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("testUser");

        when(securityUtil.getUsernameFromSecurityContext()).thenReturn("testUser");
        when(userInfoRepository.findByUsername("testUser")).thenReturn(Optional.of(userInfo));
        when(incomeRepository.save(any(Income.class))).thenReturn(new Income());

        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setAmount(BigDecimal.valueOf(100));
        incomeDto.setDescription("Salary");
        incomeDto.setDate(LocalDate.now());

        Income result = incomeService.createIncome(incomeDto);

        assertNotNull(result);
        verify(incomeRepository, times(1)).save(any(Income.class));
    }

    @Test
    void testUpdateIncome() {
        Income income = new Income();
        income.setId(1L);
        income.setAmount(BigDecimal.valueOf(100));

        when(incomeRepository.findById(1L)).thenReturn(Optional.of(income));
        when(incomeRepository.save(any(Income.class))).thenReturn(income);

        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setAmount(BigDecimal.valueOf(150));
        incomeDto.setDescription("Updated Salary");
        incomeDto.setDate(LocalDate.now());

        Income result = incomeService.updateIncome(1L, incomeDto);

        assertEquals(BigDecimal.valueOf(150), result.getAmount());
        verify(incomeRepository, times(1)).save(any(Income.class));
    }

    @Test
    void testDeleteIncome() {
        Income income = new Income();
        income.setId(1L);
        income.setAmount(BigDecimal.valueOf(100));
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("testUser");
        income.setUserInfo(userInfo);

        when(incomeRepository.findById(1L)).thenReturn(Optional.of(income));
        when(securityUtil.getUsernameFromSecurityContext()).thenReturn("testUser");

        incomeService.deleteIncome(1L);

        verify(incomeRepository, times(1)).delete(any(Income.class));
    }

}
