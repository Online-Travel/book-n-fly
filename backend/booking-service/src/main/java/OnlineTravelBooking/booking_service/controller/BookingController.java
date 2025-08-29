package OnlineTravelBooking.booking_service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import OnlineTravelBooking.booking_service.dto.BookingRequestDTO;
import OnlineTravelBooking.booking_service.dto.UpdateBookingRequestDTO;
import OnlineTravelBooking.booking_service.model.Booking;
import OnlineTravelBooking.booking_service.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {


    @Autowired
    private BookingService bookingService;


    @GetMapping("/{bookingId}")
    @PreAuthorize("hasRole('TRAVELER') or hasRole('HOTEL_MANAGER') or hasRole('TRAVEL_AGENT') or hasRole('ADMIN')")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        Optional<Booking> booking = bookingService.getBookingById(bookingId);
        return booking.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    @PreAuthorize("hasRole('TRAVELER') or hasRole('HOTEL_MANAGER') or hasRole('TRAVEL_AGENT') or hasRole('ADMIN')")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @PostMapping
    @PreAuthorize("hasRole('TRAVELER')")
    public ResponseEntity<Booking> createBooking(@RequestHeader("Authorization") String token,@RequestBody BookingRequestDTO booking) {
        Booking createdBooking = bookingService.addBooking(token.substring(7).trim(), booking);
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
    public ResponseEntity<String> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.status(HttpStatus.OK).body("Removed Booking");
    }

    @PostMapping("/hasBooked")
    @PreAuthorize("hasRole('TRAVELER')")
    public ResponseEntity<Boolean> hasBooked(@RequestBody BookingRequestDTO bookingDto) {
        Booking booking = bookingService.getBookingByDetails(
                bookingDto.getType(),
                bookingDto.getItemId(),
                bookingDto.getUserId());

        return ResponseEntity.ok(booking != null);
    }

}
