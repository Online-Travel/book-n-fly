package OnlineTravelBooking.booking_service.repository;

import OnlineTravelBooking.booking_service.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {

    @Query("SELECT f FROM Flight f " +
            "WHERE (:airline IS NULL OR f.airline = :airline) " +
            "AND (:departure IS NULL OR f.departure = :departure) " +
            "AND (:arrival IS NULL OR f.arrival = :arrival) " +
            "AND (:minPrice IS NULL OR f.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR f.price <= :maxPrice) " +
            "AND (:availability IS NULL OR f.availability = :availability)")
    List<Flight> searchFlights(
            @Param("airline") String airline,
            @Param("departure") String departure,
            @Param("arrival") String arrival,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("availability") Boolean availability
    );
}
