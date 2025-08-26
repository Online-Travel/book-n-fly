package onlineTravelBooking.review_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDTO {

    private Long reviewId;
    private Long userId;
    private Long hotelId;
    private int rating;
    private String comment;
    private LocalDateTime timestamp;
}
