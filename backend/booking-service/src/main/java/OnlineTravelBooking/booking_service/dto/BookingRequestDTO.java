package OnlineTravelBooking.booking_service.dto;

import OnlineTravelBooking.booking_service.model.BookingType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDTO {
    private Long bookingId;

    private Long itemId;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private BookingType type;

    private String status;

    private Long paymentId;

}
