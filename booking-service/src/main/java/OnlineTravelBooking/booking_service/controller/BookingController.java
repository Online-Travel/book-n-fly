package OnlineTravelBooking.booking_service.controller;

import OnlineTravelBooking.booking_service.dto.BookingRequestDTO;
import OnlineTravelBooking.booking_service.dto.UpdateBookingRequestDTO;
import OnlineTravelBooking.booking_service.model.Booking;
import OnlineTravelBooking.booking_service.service.BookingService;
import OnlineTravelBooking.booking_service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final JwtUtil jwtUtil;

    @Autowired
    private BookingService bookingService;

    public BookingController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/{bookingId}")
    @PreAuthorize("hasRole('TRAVELER') or hasRole('HOTEL_MANAGER') or hasRole('TRAVEL AGENT') or hasRole('ADMIN')")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        Optional<Booking> booking = bookingService.getBookingById(bookingId);
        return booking.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    @PreAuthorize("hasRole('TRAVELER') or hasRole('HOTEL_MANAGER') or hasRole('TRAVEL AGENT') or hasRole('ADMIN')")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @PostMapping
    @PreAuthorize("hasRole('TRAVELER')")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequestDTO booking) {
        Booking createdBooking = bookingService.addBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    @PutMapping("/{bookingId}")
    @PreAuthorize("hasRole('TRAVELER')")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long bookingId, @RequestBody UpdateBookingRequestDTO booking) {
        Booking updatedBooking = bookingService.updateBooking(bookingId, booking);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasRole('TRAVELER')")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

//    @GetMapping("/greet")
//    @PreAuthorize("hasRole('TRAVELER')")
//    public String greet(){
//        return "greet";
//    }
}
