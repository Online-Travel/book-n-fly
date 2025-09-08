package OnlineTravelBooking.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequestDTO {
    private Long hotelId;

    private String name;

    private String location;

    private Integer roomsAvailable;

    private Double rating;

    private Double pricePerNight;
}
