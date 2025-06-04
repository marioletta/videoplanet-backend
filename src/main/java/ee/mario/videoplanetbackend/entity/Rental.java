package ee.mario.videoplanetbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private RentalOrder rentalOrder;

    @ManyToOne
    private Movie movie;

    private LocalDate rentedOn;
    private LocalDate returnedOn;

    @Column(nullable = false)
    private int daysRequested;
    private int bonusPointsUsed = 0;

    private BigDecimal rentalFee;
    private BigDecimal lateFee;

    private boolean paidWithBonusPoints;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;
}
