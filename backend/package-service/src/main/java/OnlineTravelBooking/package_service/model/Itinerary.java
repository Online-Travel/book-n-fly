package OnlineTravelBooking.package_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "itinerary")
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itineraryId;

    @Column(nullable = false)
    private Long userId;

    private long packageId;


    @Column(length = 2000)
    private String customizationDetails;

    // New fields - let's add them one by one
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer numberOfTravelers = 1;

    private Double totalPrice;

    private String status = "DRAFT";

}
