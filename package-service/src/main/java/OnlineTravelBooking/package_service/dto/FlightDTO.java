package OnlineTravelBooking.package_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {

	private Long flightId;

    private String airline;

    private String departure;

    private String arrival;

    private Double price;

    private Boolean availability;
}
