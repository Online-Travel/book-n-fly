package OnlineTravelBooking.package_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "packages")
public class TravelPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private Long packageId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private List<Long> includedHotelIds = new ArrayList<>();

    @Column(nullable = false)
    private List<Long> includedFlightIds = new ArrayList<>();

    @Column(nullable = false)
    private List<String> activities = new ArrayList<>();

    @Column(nullable = false)
    private Double price;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Integer durationDays;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private Long createdByAgentId; // Travel Agent who created this package

    private Boolean isActive = true;
}
