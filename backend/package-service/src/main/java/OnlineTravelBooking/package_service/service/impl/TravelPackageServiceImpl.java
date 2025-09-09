package OnlineTravelBooking.package_service.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import OnlineTravelBooking.package_service.dto.FlightDTO;
import OnlineTravelBooking.package_service.dto.HotelDTO;
import OnlineTravelBooking.package_service.feignclient.FlightClient;
import OnlineTravelBooking.package_service.feignclient.HotelClient;
import OnlineTravelBooking.package_service.model.TravelPackage;
import OnlineTravelBooking.package_service.repository.TravelPackageRepository;
import OnlineTravelBooking.package_service.service.TravelPackageService;
import OnlineTravelBooking.package_service.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TravelPackageServiceImpl implements TravelPackageService {

    private final TravelPackageRepository repo;

    @Autowired
    private FlightClient flightClient;

    @Autowired
    private HotelClient hotelClient;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public TravelPackageServiceImpl(TravelPackageRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<TravelPackage> getAllPackages() {
        return repo.findAll();
    }

    @Override
    public List<TravelPackage> getActivePackages() {
        return repo.findAll().stream()
                .filter(TravelPackage::getIsActive)
                .collect(Collectors.toList());
    }

    @Override
    public TravelPackage savePackage(String token, TravelPackage travelPackage) {
        travelPackage.setIsActive(true);
        travelPackage.setCreatedByAgentId(jwtUtil.extractUserId(token));

        String destination = travelPackage.getDestination();
        List<HotelDTO> hotels = new ArrayList<>();
        List<FlightDTO> flights = new ArrayList<>();

        try {
            hotels = hotelClient.searchHotels(destination, null, null, null);
            flights = flightClient.searchFlights(null, null, destination, null, null, true);
        } catch (Exception e) {
            System.err.println("Error fetching hotels/flights: " + e.getMessage());
        }

        if (!hotels.isEmpty()) {
            HotelDTO bestHotel = hotels.stream()
                    .min(Comparator.comparingDouble(HotelDTO::getPricePerNight))
                    .orElse(hotels.get(0));
            travelPackage.setIncludedHotelIds(List.of(bestHotel.getHotelId()));
        } else {
            travelPackage.setIncludedHotelIds(new ArrayList<>());
        }

        if (!flights.isEmpty()) {
            FlightDTO bestFlight = flights.stream()
                    .min(Comparator.comparingDouble(FlightDTO::getPrice))
                    .orElse(flights.get(0));
            travelPackage.setIncludedFlightIds(List.of(bestFlight.getFlightId()));
        } else {
            travelPackage.setIncludedFlightIds(new ArrayList<>());
        }

        if (travelPackage.getActivities() == null || travelPackage.getActivities().isEmpty()) {
            throw new IllegalArgumentException("Activities cannot be null or empty.");
        }

        return repo.save(travelPackage);
    }

    @Override
    public Optional<TravelPackage> getPackageById(Long id) {
        return repo.findById(id);
    }

    @Override
    public TravelPackage updatePackage(Long id, TravelPackage travelPackage) {
        TravelPackage existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        existing.setName(travelPackage.getName());
        existing.setPrice(travelPackage.getPrice());
        existing.setDescription(travelPackage.getDescription());
        existing.setDestination(travelPackage.getDestination());
        existing.setActivities(travelPackage.getActivities());
        existing.setDurationDays(travelPackage.getDurationDays());

        return repo.save(existing);
    }

    @Override
    public TravelPackage makePackageInactive(Long id) {
        Optional<TravelPackage> optionalPkg = repo.findById(id);
        if (optionalPkg.isPresent()) {
            TravelPackage pkg = optionalPkg.get();
            pkg.setIsActive(false);
            return repo.save(pkg);
        }
        return null;
    }

    @Override
    public TravelPackage makePackageActive(Long id) {
        Optional<TravelPackage> optionalPkg = repo.findById(id);
        if (optionalPkg.isPresent()) {
            TravelPackage pkg = optionalPkg.get();
            pkg.setIsActive(true);
            return repo.save(pkg);
        }
        return null;
    }

    @Override
    public void hardDeletePackage(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<TravelPackage> getPackagesByDestination(String destination) {
        return repo.findByDestinationContainingIgnoreCase(destination);
    }

    @Override
    public List<TravelPackage> getPackagesByPriceRange(Double minPrice, Double maxPrice) {
        return repo.findAll().stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    @Override
    public Double getPackagePrice(long packageId) {
        return repo.findById(packageId)
                   .map(TravelPackage::getPrice)
                   .orElse(null);
    }
    
    @Override
    public List<TravelPackage> getPackagesByAgent(Long agentId) {
        return repo.findAll().stream()
                .filter(p -> p.getCreatedByAgentId().equals(agentId))
                .collect(Collectors.toList());
    }
}
