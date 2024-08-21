package uz.uzum.financeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uzum.financeapp.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}