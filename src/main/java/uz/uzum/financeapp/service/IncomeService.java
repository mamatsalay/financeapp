package uz.uzum.financeapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.uzum.financeapp.dto.IncomeDto;
import uz.uzum.financeapp.exception.ResourceNotFoundException;
import uz.uzum.financeapp.model.Income;
import uz.uzum.financeapp.model.UserInfo;
import uz.uzum.financeapp.repository.IncomeRepository;
import uz.uzum.financeapp.repository.UserInfoRepository;
import uz.uzum.financeapp.security.SecurityUtil;


@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserInfoRepository userInfoRepository;
    private final SecurityUtil securityUtil;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository, UserInfoRepository userInfoRepository, SecurityUtil securityUtil) {
        this.incomeRepository = incomeRepository;
        this.userInfoRepository = userInfoRepository;
        this.securityUtil = securityUtil;
    }

    public Income createIncome(IncomeDto incomeDto) {
        UserInfo userInfo = getAuthenticatedUser();
        Income income = new Income(userInfo, incomeDto.getAmount(), incomeDto.getDescription(), incomeDto.getDate());
        return incomeRepository.save(income);
    }

    public Income updateIncome(Long id ,IncomeDto incomeDto) {
        Income income = incomeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Income not Found"));

        income.setAmount(incomeDto.getAmount());
        income.setDescription(incomeDto.getDescription());
        income.setDate(incomeDto.getDate());
        return incomeRepository.save(income);
    }

    public void deleteIncome(Long id) {
        Income income = incomeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Income not Found"));

        if(!isAdmin() && !income.getUserInfo().getUsername().equals(securityUtil.getUsernameFromSecurityContext())){
            throw new AccessDeniedException("You do not have permission to delete this income");
        }

        incomeRepository.delete(income);
    }

    private boolean isAdmin() {
        return getAuthenticatedUser().getRoles().contains("ADMIN");
    }

    private UserInfo getAuthenticatedUser() {
        String username = securityUtil.getUsernameFromSecurityContext();
        return userInfoRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
