package OnlineTravelBooking.package_service.service;

import java.util.List;
import java.util.Optional;

import OnlineTravelBooking.package_service.model.TravelPackage;

public interface TravelPackageService {

    List<TravelPackage> getAllPackages();

    List<TravelPackage> getActivePackages();

    TravelPackage savePackage(String token, TravelPackage travelPackage);

    Optional<TravelPackage> getPackageById(Long id);

    TravelPackage updatePackage(Long id, TravelPackage travelPackage);

    Double getPackagePrice(long packageId);

    TravelPackage makePackageInactive(Long id);

    TravelPackage makePackageActive(Long id);

    void hardDeletePackage(Long id);

    List<TravelPackage> getPackagesByDestination(String destination);

    List<TravelPackage> getPackagesByPriceRange(Double minPrice, Double maxPrice);

    List<TravelPackage> getPackagesByAgent(Long agentId);
}
