package onlineTravelBooking.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlineTravelBooking.payment_service.entity.Payment;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPaymentResponseDTO {

    private UserDTO user;
    private List<PaymentResponseDTO> payments;
}
