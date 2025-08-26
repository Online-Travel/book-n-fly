package OnlineTravelBooking.package_service.controller;

import OnlineTravelBooking.package_service.model.Itinerary;
import OnlineTravelBooking.package_service.service.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import OnlineTravelBooking.package_service.utils.JwtUtil;


import java.util.List;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {

    private final JwtUtil jwtUtil;


    private final ItineraryService service;

    public ItineraryController(ItineraryService service,JwtUtil jwtUtil) {

        this.service = service;
        this.jwtUtil=jwtUtil;
    }

    @GetMapping
    public List<Itinerary> getAll() {

        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Itinerary> get(@PathVariable Long id) {
        return ResponseEntity.of(service.getItineraryById(id));
    }

    @GetMapping("/user/{userId}")
    public List<Itinerary> getUserItineraries(@PathVariable Long userId) {
        return service.getItinerariesByUserId(userId);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('TRAVEL_AGENT','TRAVELER')")
    public Itinerary create(@RequestHeader("Authorization") String authHeader, @RequestBody Itinerary itinerary) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7).trim() : authHeader;
        Long userId = jwtUtil.extractUserId(token);
        itinerary.setUserId(userId);
        return service.save(itinerary);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENT','ADMIN','TRAVELER')")
    public Itinerary update(@PathVariable Long id, @RequestBody Itinerary itinerary) {
        return service.update(id, itinerary);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENT','ADMIN','TRAVELER')")
    public ResponseEntity<Itinerary> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(service.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENT','ADMIN','TRAVELER')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
