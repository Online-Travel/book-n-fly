package OnlineTravelBooking.package_service.repository;

import OnlineTravelBooking.package_service.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    // Find itineraries by user ID
    List<Itinerary> findByUserId(Long userId);

    // Find itineraries by status
    List<Itinerary> findByStatus(String status);


    // Find itineraries by package ID
    List<Itinerary> findByPackageId(Long packageId);

    // Find itineraries by user and status
    List<Itinerary> findByUserIdAndStatus(Long userId, String status);
}
