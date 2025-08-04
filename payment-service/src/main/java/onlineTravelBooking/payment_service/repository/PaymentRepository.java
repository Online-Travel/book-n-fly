package onlineTravelBooking.payment_service.repository;

import onlineTravelBooking.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    List<Payment> findByUserId(Long userId);

    Payment findByBookingId(Long bookingId);
}
