package OnlineTravelBooking.package_service.feignclient;

import OnlineTravelBooking.package_service.dto.FlightDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="BOOKING-SERVICE")
public interface FlightClient {

    @GetMapping("/api/flights/search")
    List<FlightDTO> searchFlights(
            @RequestParam(required = false) String airline,
            @RequestParam(required = false) String departure,
            @RequestParam(required = false) String arrival,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean availability
    );

//    @GetMapping
//    List<FlightDTO> getFlightsByDestination(@PathVariable String destination);
}
