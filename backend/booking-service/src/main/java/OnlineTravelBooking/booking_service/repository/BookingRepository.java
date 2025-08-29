package OnlineTravelBooking.booking_service.repository;

import OnlineTravelBooking.booking_service.model.Booking;
import OnlineTravelBooking.booking_service.model.BookingType;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    Booking findByTypeAndItemIdAndUserId(BookingType type, Long itemId, Long userId);

    Long userId(Long userId);
}
