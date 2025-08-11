package OnlineTravelBooking.booking_service.service;

import OnlineTravelBooking.booking_service.model.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightService {

    List<Flight> getAllFlights();

    Optional<Flight> getFlightById(Long flightId);

    Flight addFlight(Flight flight);

    Flight updateFlight(Long flightId, Flight flight);

    void deleteFlight(Long flightId);
}
