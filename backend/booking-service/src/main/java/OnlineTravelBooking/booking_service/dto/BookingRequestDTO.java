package OnlineTravelBooking.booking_service.dto;

import OnlineTravelBooking.booking_service.model.BookingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDTO {

    private BookingType type;

}
