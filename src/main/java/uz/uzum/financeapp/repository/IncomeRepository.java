package uz.uzum.financeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.uzum.financeapp.model.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

}