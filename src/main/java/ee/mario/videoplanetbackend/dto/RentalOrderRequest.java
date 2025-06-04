package ee.mario.videoplanetbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class RentalOrderRequest {
    private Long customerId;
    private List<RentalRequestItem> rentals;
    private boolean useBonusPoints;
}
