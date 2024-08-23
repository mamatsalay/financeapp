package uz.uzum.financeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.uzum.financeapp.model.Income;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    Optional<Income> findIncomeById(Long id);

    @Query("SELECT i FROM Income i WHERE i.userInfo.username = :username AND i.date BETWEEN :startDate AND :endDate")
    List<Income> findIncomesByDateRangeAndUser(
            @Param("username") String username,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}