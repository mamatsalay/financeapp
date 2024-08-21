package uz.uzum.financeapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userInfo_id", nullable = false)
    private UserInfo userInfo;

    public Income(UserInfo userInfo, BigDecimal amount, String description, LocalDate date) {
        this.userInfo = userInfo;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

}
