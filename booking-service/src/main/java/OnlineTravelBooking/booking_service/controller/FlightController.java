package OnlineTravelBooking.booking_service.controller;

import OnlineTravelBooking.booking_service.model.Flight;
import OnlineTravelBooking.booking_service.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @GetMapping("/{flightId}")
//    @PreAuthorize("hasRole('TRAVELER') or hasRole('HOTEL_MANAGER') or hasRole('TRAVEL_AGENT') or hasRole('ADMIN')")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long flightId) {
        Optional<Flight> flight = flightService.getFlightById(flightId);
        return flight.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
//    @PreAuthorize("hasRole('TRAVELER') or hasRole('HOTEL_MANAGER') or hasRole('TRAVEL_AGENT') or hasRole('ADMIN')")
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        Flight createdFlight = flightService.addFlight(flight);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
    }

    @PutMapping("/{flightId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long flightId, @RequestBody Flight flight) {
        Flight updatedFlight = flightService.updateFlight(flightId, flight);
        return ResponseEntity.ok(updatedFlight);
    }

    @DeleteMapping("/{flightId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long flightId) {
        flightService.deleteFlight(flightId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
