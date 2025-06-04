package ee.mario.videoplanetbackend.dto;

import ee.mario.videoplanetbackend.entity.MovieType;
import ee.mario.videoplanetbackend.entity.Rental;
import ee.mario.videoplanetbackend.entity.RentalOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class RentalOrderResponse {
    private Long orderId;
    private String customerName;
    private List<String> rentedMovies;
    private BigDecimal totalPrice;
    private int bonusPointsEarned;
    private int bonusPointsUsed;
    private int remainingBonusPoints;

    public RentalOrderResponse(RentalOrder order, int bonusPointsUsed) {
        this.orderId = order.getId();
        this.customerName = order.getCustomer().getName();
        this.rentedMovies = order.getRentals().stream()
                .map(r -> r.getMovie().getTitle())
                .collect(Collectors.toList());
        this.totalPrice = order.getRentals().stream()
                .map(Rental::getRentalFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.bonusPointsUsed = bonusPointsUsed;
        this.bonusPointsEarned = order.getRentals().stream()
                .mapToInt(r -> r.getMovie().getType() == MovieType.NEW ? 2 : 1)
                .sum();
        this.remainingBonusPoints = order.getCustomer().getBonusPoints();
    }
}
