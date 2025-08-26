package OnlineTravelBooking.package_service.feignclient;

import OnlineTravelBooking.package_service.dto.HotelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="BOOKING-SERVICE")
public interface HotelClient {

    @GetMapping("/api/hotels/search")
    List<HotelDTO> searchHotels(
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minRooms
    );

//    @GetMapping
//    List<HotelDTO> getHotelsByDestination(@PathVariable String destination);
}
