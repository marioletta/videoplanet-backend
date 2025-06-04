package ee.mario.videoplanetbackend.dto;

import ee.mario.videoplanetbackend.entity.Rental;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalReturnResponse {
    private Long rentalId;
    private String movieTitle;
    private String customerName;
    private LocalDate rentedOn;
    private LocalDate returnedOn;
    private int daysRequested;
    private long daysKept;
    private BigDecimal lateFee;
    private int bonusPointsEarned;

    public RentalReturnResponse(Rental rental, long daysKept, BigDecimal lateFee, int bonusPointsEarned) {
        this.rentalId = rental.getId();
        this.movieTitle = rental.getMovie().getTitle();
        this.customerName = rental.getCustomer().getName();
        this.rentedOn = rental.getRentedOn();
        this.returnedOn = rental.getReturnedOn();
        this.daysRequested = rental.getDaysRequested();
        this.daysKept = daysKept;
        this.lateFee = lateFee;
        this.bonusPointsEarned = bonusPointsEarned;
    }
}
