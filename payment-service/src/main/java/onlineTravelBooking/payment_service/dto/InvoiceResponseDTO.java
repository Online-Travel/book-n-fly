package onlineTravelBooking.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponseDTO {

    private Long invoiceId;
    private Long bookingId;
    private Long userId;
    private double amount;
    private LocalDateTime timestamp;

}
