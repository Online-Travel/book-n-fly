package onlineTravelBooking.payment_service.repository;

import onlineTravelBooking.payment_service.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

    List<Invoice> findAllByUserId(Long userId);

    List<Invoice> findAllByBookingId(Long bookingId);

    Optional<Invoice> findByPayment_PaymentId(Long paymentId);

}
