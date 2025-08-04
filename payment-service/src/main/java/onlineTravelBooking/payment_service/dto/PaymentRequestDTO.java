package onlineTravelBooking.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {

    private Long userId;
    private Long bookingId;
    private double amount;
    private String paymentMethod;
}
