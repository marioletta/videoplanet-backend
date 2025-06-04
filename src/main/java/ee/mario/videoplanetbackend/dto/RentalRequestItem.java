package ee.mario.videoplanetbackend.dto;

import lombok.Data;

@Data
public class RentalRequestItem {
    private Long movieId;
    private int daysRequested;
}
