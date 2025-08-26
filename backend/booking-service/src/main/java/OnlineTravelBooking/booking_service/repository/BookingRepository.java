package OnlineTravelBooking.booking_service.repository;

import OnlineTravelBooking.booking_service.model.Booking;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
}
