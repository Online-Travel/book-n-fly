package onlineTravelBooking.review_service.dto;

import lombok.Data;
import onlineTravelBooking.review_service.entity.BookingType;

@Data
public class BookingDto {
    private BookingType type;
    private Long itemId; // This will be hotelId
    private Long userId;
}
