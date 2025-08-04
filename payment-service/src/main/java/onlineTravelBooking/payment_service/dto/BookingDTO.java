package onlineTravelBooking.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private Long bookingId;
    private Long userId;
    private String Type;
    private String status;
    private Long payment_id;


}
