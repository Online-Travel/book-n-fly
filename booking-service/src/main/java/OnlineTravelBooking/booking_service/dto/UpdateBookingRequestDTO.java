package OnlineTravelBooking.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingRequestDTO {

    private String status;

    private Long paymentId;
}
