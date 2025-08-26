package OnlineTravelBooking.package_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {

    private Long hotelId;
    private String name;
    private Double rating;
    private String location;
    private Integer roomsAvailable;
    private Double pricePerNight;
}
