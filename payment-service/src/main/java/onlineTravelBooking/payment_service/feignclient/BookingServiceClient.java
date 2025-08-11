package onlineTravelBooking.payment_service.feignclient;

import onlineTravelBooking.payment_service.dto.BookingDTO;
import onlineTravelBooking.payment_service.dto.UpdateBookingRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="BOOKING-SERVICE")
public interface BookingServiceClient {

    @PutMapping("/api/bookings/{bookingId}")
    @PreAuthorize("hasRole('TRAVELER')")
    public BookingDTO updateBooking(@RequestHeader("Authorization") String token,@PathVariable Long bookingId, @RequestBody UpdateBookingRequestDTO booking);

    @GetMapping("/api/bookings/{bookingId}")
    @PreAuthorize("hasRole('TRAVELER') or hasRole('HOTEL_MANAGER') or hasRole('TRAVEL AGENT') or hasRole('ADMIN')")
    public BookingDTO getBookingById(@RequestHeader("Authorization") String token,@PathVariable Long bookingId);

    @GetMapping("/api/bookings")
    @PreAuthorize("hasRole('TRAVELER') or hasRole('HOTEL_MANAGER') or hasRole('TRAVEL AGENT') or hasRole('ADMIN')")
    public List<BookingDTO> getAllBookings(@RequestHeader("Authorization") String token);
}

