package OnlineTravelBooking.booking_service.controller;

import OnlineTravelBooking.booking_service.model.Hotel;
import OnlineTravelBooking.booking_service.service.HotelService;
import OnlineTravelBooking.booking_service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private HotelService hotelService;
    private final JwtUtil jwtUtil;

    @Autowired
    public HotelController(HotelService hotelService, JwtUtil jwtUtil) {
        this.hotelService = hotelService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/{hotelId}")
    @PreAuthorize("hasRole('TRAVELER')")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long hotelId) {
        Optional<Hotel> hotel = hotelService.getHotelById(hotelId);
        return hotel.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/my-hotels")
    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    public ResponseEntity<List<Hotel>> getMyHotels(@RequestHeader("Authorization") String token) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7).trim() : token.trim();
        Long userId = jwtUtil.extractUserId(jwt);

        List<Hotel> myHotels = hotelService.getHotelsByUserId(userId);
        return ResponseEntity.ok(myHotels);
    }

    @PostMapping
    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    public ResponseEntity<Hotel> createHotel(@RequestHeader("Authorization") String token, @RequestBody Hotel hotel) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7).trim() : token.trim();
        Long userId = jwtUtil.extractUserId(jwt);

        hotel.setUserId(userId);

        Hotel createdHotel = hotelService.addHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
    }

    @PutMapping("/{hotelId}")
    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    public ResponseEntity<Hotel> updateHotel(@RequestHeader("Authorization") String token, @PathVariable Long hotelId, @RequestBody Hotel hotel) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7).trim() : token.trim();
        Long userId = jwtUtil.extractUserId(jwt);
        hotel.setUserId(userId);

        Hotel updatedHotel = hotelService.updateHotel(userId, hotelId, hotel);
        return ResponseEntity.ok(updatedHotel);
    }

    @DeleteMapping("/{hotelId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteHotel(@PathVariable Long hotelId) {
        hotelService.deleteHotel(hotelId);
        return ResponseEntity.status(HttpStatus.OK).body("Hotel Deleted");
    }

    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxRating,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minRooms,
            @RequestParam(required = false) Integer maxRooms
    ) {
        List<Hotel> hotels = hotelService.searchHotels(destination, minRating, maxRating, minPrice, maxPrice, minRooms, maxRooms);
        return ResponseEntity.ok(hotels);
    }
}
