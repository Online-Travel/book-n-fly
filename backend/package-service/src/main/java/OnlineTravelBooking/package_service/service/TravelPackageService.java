package OnlineTravelBooking.package_service.service;

import OnlineTravelBooking.package_service.model.TravelPackage;

import java.util.List;
import java.util.Optional;

public interface TravelPackageService {

    List<TravelPackage> getAllPackages();

    List<TravelPackage> getActivePackages();

    TravelPackage savePackage(String token, TravelPackage travelPackage);

    Optional<TravelPackage> getPackageById(Long id);

    TravelPackage updatePackage(Long id, TravelPackage travelPackage);

    void deletePackage(Long id);

    void hardDeletePackage(Long id);

    List<TravelPackage> getPackagesByDestination(String destination);

    List<TravelPackage> getPackagesByPriceRange(Double minPrice, Double maxPrice);

    List<TravelPackage> getPackagesByAgent(Long agentId);
}
