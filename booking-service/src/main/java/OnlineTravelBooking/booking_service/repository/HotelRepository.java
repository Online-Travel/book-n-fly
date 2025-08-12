package OnlineTravelBooking.booking_service.repository;

import OnlineTravelBooking.booking_service.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByLocationIgnoreCase(String location);

    @Query("SELECT h FROM Hotel h " +
            "WHERE (:location IS NULL OR LOWER(h.location) = LOWER(:location)) " +
            "AND (:minRating IS NULL OR h.rating >= :minRating) " +
            "AND (:maxPrice IS NULL OR h.pricePerNight <= :maxPrice) " +
            "AND (:minRooms IS NULL OR h.roomsAvailable >= :minRooms)")
    List<Hotel> searchHotels(
            @Param("location") String location,
            @Param("minRating") Double minRating,
            @Param("maxPrice") Double maxPrice,
            @Param("minRooms") Integer minRooms
    );
}
