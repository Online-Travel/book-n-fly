package onlineTravelBooking.review_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import onlineTravelBooking.review_service.dto.BookingDto;

@FeignClient(
	    name = "booking-service",
	    url = "http://localhost:8080/api/bookings",
	    configuration = FeignClientConfig.class
	)
public interface BookingClient {
    
    @PostMapping("/hasBooked")
    Boolean hasBooked(@RequestBody BookingDto bookingDto);
}