package onlineTravelBooking.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingPaymentResponseDTO {

    private BookingDTO bookingDTO;
    private List<PaymentResponseDTO> paymentResponseDTO;
}
