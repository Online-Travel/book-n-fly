package onlineTravelBooking.payment_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private Long userId;
    private Long bookingId;
    private double amount;
    private  String status;
    private String paymentMethod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="invoice_id")
    private Invoice invoice;

}
