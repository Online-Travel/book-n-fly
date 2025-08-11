package OnlineTravelBooking.booking_service.repository;

import OnlineTravelBooking.booking_service.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {
}
