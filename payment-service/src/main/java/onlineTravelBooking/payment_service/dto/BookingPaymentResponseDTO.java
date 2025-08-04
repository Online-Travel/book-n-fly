package onlineTravelBooking.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingPaymentResponseDTO {

    private BookingDTO bookingDTO;
    private PaymentResponseDTO paymentResponseDTO;
}
