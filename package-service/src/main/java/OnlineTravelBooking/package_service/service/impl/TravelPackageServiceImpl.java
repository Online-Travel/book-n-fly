package OnlineTravelBooking.package_service.service.impl;


import OnlineTravelBooking.package_service.dto.FlightDTO;
import OnlineTravelBooking.package_service.dto.HotelDTO;
import OnlineTravelBooking.package_service.model.TravelPackage;
import OnlineTravelBooking.package_service.repository.TravelPackageRepository;
import OnlineTravelBooking.package_service.service.TravelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import OnlineTravelBooking.package_service.feignclient.HotelClient;
import OnlineTravelBooking.package_service.feignclient.FlightClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TravelPackageServiceImpl implements TravelPackageService {

    private final TravelPackageRepository repo;

    @Autowired
    private FlightClient flightClient;

    @Autowired
    private HotelClient hotelClient;

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
    public TravelPackage savePackage(TravelPackage travelPackage) {
        travelPackage.setIsActive(true);

        String destination = travelPackage.getDestination();

        // ✅ Fetch hotels and flights from other services
        List<HotelDTO> hotels=new ArrayList<>();
        List<FlightDTO> flights=new ArrayList<>();
        try {
            hotels = hotelClient.searchHotels(destination, null, null, null);
            flights = flightClient.searchFlights(null, destination, null, null, null, true);

        }
        catch (Exception e){
            System.err.println("Error in fetching the hotel"+e.getMessage());
        }

        if (hotels.isEmpty()) {
            System.err.println("No hotels found for destination: " + destination);
            // Option 1: Create package without hotel (set empty list)
            travelPackage.setIncludedHotelIds(new ArrayList<>());

            // Option 2: Throw exception (uncomment if you want strict validation)
            // throw new RuntimeException("No hotels available for destination: " + destination +
            //     ". Please ensure booking service is running and has hotel data.");
        } else {
            // ✅ Pick the cheapest hotel
            HotelDTO bestHotel = hotels.stream()
                    .min(Comparator.comparingDouble(HotelDTO::getPrice))
                    .orElse(hotels.get(0)); // Fallback to first hotel
            travelPackage.setIncludedHotelIds(List.of(bestHotel.getId()));
        }

        // ✅ Handle empty flight list
        if (flights.isEmpty()) {
            System.err.println("No flights found for destination: " + destination);
            // Option 1: Create package without flight (set empty list)
            travelPackage.setIncludedFlightIds(new ArrayList<>());

            // Option 2: Throw exception (uncomment if you want strict validation)
            // throw new RuntimeException("No flights available for destination: " + destination +
            //     ". Please ensure booking service is running and has flight data.");
        } else {
            // ✅ Pick the cheapest flight
            FlightDTO bestFlight = flights.stream()
                    .min(Comparator.comparingDouble(FlightDTO::getPrice))
                    .orElse(flights.get(0)); // Fallback to first flight
            travelPackage.setIncludedFlightIds(List.of(bestFlight.getId()));
        }

        // ✅ Ensure activities are provided
        if (travelPackage.getActivities() == null || travelPackage.getActivities().isEmpty()) {
            throw new IllegalArgumentException("Activities cannot be null or empty.");
        }

        return repo.save(travelPackage);

        // ✅ Pick the cheapest hotel
//        HotelDTO bestHotel = hotels.stream()
//                .min(Comparator.comparingDouble(HotelDTO::getPrice))
//                .orElseThrow(() -> new RuntimeException("No hotels found for destination: " + destination));
//
//        // ✅ Pick the cheapest flight
//        FlightDTO bestFlight = flights.stream()
//                .min(Comparator.comparingDouble(FlightDTO::getPrice))
//                .orElseThrow(() -> new RuntimeException("No flights found for destination: " + destination));
//
//        // ✅ Set selected IDs into the package
//        travelPackage.setIncludedHotelIds(List.of(bestHotel.getId()));
//        travelPackage.setIncludedFlightIds(List.of(bestFlight.getId()));
//
//        // ✅ Ensure activities are provided by travel agent
//        if (travelPackage.getActivities() == null || travelPackage.getActivities().isEmpty()) {
//            throw new IllegalArgumentException("Activities cannot be null or empty.");
//        }
//
//        return repo.save(travelPackage);
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
        existing.setDescription(travelPackage.getDescription());
        existing.setPrice(travelPackage.getPrice());
        existing.setDestination(travelPackage.getDestination());
        existing.setCreatedByAgentId(travelPackage.getCreatedByAgentId());
        existing.setActivities(travelPackage.getActivities());
        existing.setIncludedFlightIds(travelPackage.getIncludedFlightIds());
        existing.setIncludedHotelIds(travelPackage.getIncludedHotelIds());
        existing.setDurationDays(travelPackage.getDurationDays());

        return repo.save(existing);
    }

    @Override
    public void deletePackage(Long id) {
        TravelPackage pkg = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));
        pkg.setIsActive(false);
        repo.save(pkg);
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
    public List<TravelPackage> getPackagesByAgent(Long agentId) {
        return repo.findAll().stream()
                .filter(p -> p.getCreatedByAgentId().equals(agentId))
                .collect(Collectors.toList());
    }
}
