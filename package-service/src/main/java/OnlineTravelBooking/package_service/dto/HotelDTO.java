package OnlineTravelBooking.package_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {

    private Long id;
    private String name;
    private Double price;
    private Double rating;
}
