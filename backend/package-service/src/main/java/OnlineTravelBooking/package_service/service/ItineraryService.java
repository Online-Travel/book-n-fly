package OnlineTravelBooking.package_service.service;

import java.util.List;
import java.util.Optional;

import OnlineTravelBooking.package_service.model.Itinerary;

public interface ItineraryService {

    List<Itinerary> getAll();

    Optional<Itinerary> getItineraryById(Long id);

    Itinerary save(Itinerary itinerary);

    Itinerary update(Long id, Itinerary itinerary);

    void delete(Long id);

    Itinerary updateStatus(Long id, String status);

    List<Itinerary> getItinerariesByUserId(Long userId);

    List<Itinerary> getItinerariesByAgent(Long agentId);
}
