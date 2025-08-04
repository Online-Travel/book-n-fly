package onlineTravelBooking.payment_service.feignclient;

import onlineTravelBooking.payment_service.dto.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="BOOKING-SERVICE")
public interface BookingServiceClient {

    @GetMapping("/booking/{booking_id}")
    BookingDTO getBookingById(@PathVariable("booking_id") Long bookingId);

    @GetMapping("/booking")
    List<BookingDTO> getAllBooking();

    @PutMapping("/booking/{bookingId}/payment")
    ResponseEntity<String> updateBookingWithPaymentId(@PathVariable("bookingId") Long bookingId, @RequestParam("paymentId") Long paymentId);

}
