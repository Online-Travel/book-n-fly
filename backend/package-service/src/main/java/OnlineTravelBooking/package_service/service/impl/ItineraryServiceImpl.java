package OnlineTravelBooking.package_service.service.impl;
import OnlineTravelBooking.package_service.model.TravelPackage;
import OnlineTravelBooking.package_service.model.Itinerary;
import OnlineTravelBooking.package_service.repository.ItineraryRepository;
import OnlineTravelBooking.package_service.service.TravelPackageService;
import OnlineTravelBooking.package_service.service.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItineraryServiceImpl implements ItineraryService {

    private final ItineraryRepository repo;
    private final TravelPackageService packageService;

    @Autowired
    public ItineraryServiceImpl(ItineraryRepository repo, TravelPackageService packageService) {
        this.repo = repo;
        this.packageService = packageService;
    }

    @Override
    public List<Itinerary> getAll() {

        return repo.findAll();
    }

    @Override
    public Optional<Itinerary> getItineraryById(Long id) {

        return repo.findById(id);
    }

    @Override
    public Itinerary save(Itinerary itinerary) {

        return repo.save(itinerary);
    }

    @Override
    public Itinerary update(Long id, Itinerary itinerary) {
        Itinerary existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerary not found"));
        existing.setCustomizationDetails(itinerary.getCustomizationDetails());
        existing.setStartDate(itinerary.getStartDate());
        existing.setEndDate(itinerary.getEndDate());
        existing.setNumberOfTravelers(itinerary.getNumberOfTravelers());
        existing.setTotalPrice(itinerary.getTotalPrice());
        existing.setStatus(itinerary.getStatus());
        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {

        repo.deleteById(id);
    }

    @Override
    public List<Itinerary> getItinerariesByUserId(Long userId) {

        return repo.findByUserId(userId);
    }

    @Override
    public Itinerary updateStatus(Long id, String status) {
        Itinerary itinerary = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerary not found"));
        itinerary.setStatus(status);
        return repo.save(itinerary);
    }
    @Override
    public List<Itinerary> getItinerariesByAgent(Long agentId) {
        // find all packages created by this agent
        List<TravelPackage> agentPackages = packageService.getPackagesByAgent(agentId);

        // extract package IDs
        List<Long> packageIds = agentPackages.stream()
                                             .map(TravelPackage::getPackageId)
                                             .toList();

        if (packageIds.isEmpty()) {
            return List.of(); // agent created no packages -> no itineraries
        }

        // fetch itineraries linked to those package IDs
        return repo.findByPackageIdIn(packageIds);
    }
}
