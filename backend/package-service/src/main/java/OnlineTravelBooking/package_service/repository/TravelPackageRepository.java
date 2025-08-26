package OnlineTravelBooking.package_service.repository;


import OnlineTravelBooking.package_service.model.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {

    // Find active packages
    List<TravelPackage> findByIsActiveTrue();

    // Find packages by destination (case insensitive and partial match)
    List<TravelPackage> findByDestinationContainingIgnoreCaseAndIsActiveTrue(String destination);

    List<TravelPackage> findByDestinationContainingIgnoreCase(String destination);

    // Find packages by price range
    List<TravelPackage> findByPriceBetweenAndIsActiveTrue(Double minPrice, Double maxPrice);

    // Find packages created by a specific agent
    List<TravelPackage> findByCreatedByAgentId(Long agentId);

    // Find packages by duration
    List<TravelPackage> findByDurationDaysAndIsActiveTrue(Integer durationDays);

    // Custom query to search packages by multiple criteria
    @Query("SELECT p FROM TravelPackage p WHERE " +
            "(:destination IS NULL OR LOWER(p.destination) LIKE LOWER(CONCAT('%', :destination, '%'))) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:minDuration IS NULL OR p.durationDays >= :minDuration) AND " +
            "(:maxDuration IS NULL OR p.durationDays <= :maxDuration) AND " +
            "p.isActive = true")
    List<TravelPackage> findPackagesByCriteria(
            @Param("destination") String destination,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minDuration") Integer minDuration,
            @Param("maxDuration") Integer maxDuration
    );
}
