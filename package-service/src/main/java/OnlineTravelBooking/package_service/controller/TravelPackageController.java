package OnlineTravelBooking.package_service.controller;


import OnlineTravelBooking.package_service.model.TravelPackage;
import OnlineTravelBooking.package_service.service.TravelPackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class TravelPackageController {

    private final TravelPackageService service;

    public TravelPackageController(TravelPackageService service) {

        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('TRAVEL AGENT')")
    public TravelPackage create(@RequestBody TravelPackage pkg) {

        return service.savePackage(pkg);
    }

    @GetMapping
    public List<TravelPackage> getAll() {

        return service.getAllPackages();
    }

    @GetMapping("/active")
    public List<TravelPackage> getActive() {

        return service.getActivePackages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelPackage> get(@PathVariable Long id) {
        return ResponseEntity.of(service.getPackageById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TRAVEL AGENT','ADMIN')")
    public TravelPackage update(@PathVariable Long id, @RequestBody TravelPackage pkg) {
        return service.updatePackage(id, pkg);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TRAVEL AGENT','ADMIN')")
    public void delete(@PathVariable Long id) {

        service.deletePackage(id);
    }

    @DeleteMapping("/{id}/hard")
    @PreAuthorize("hasRole('ADMIN')")
    public void hardDelete(@PathVariable Long id) {

        service.hardDeletePackage(id);
    }

    @GetMapping("/destination")
    public List<TravelPackage> searchByDestination(@RequestParam String destination) {
        return service.getPackagesByDestination(destination);
    }

    @GetMapping("/price")
    public List<TravelPackage> searchByPrice(@RequestParam Double min, @RequestParam Double max) {
        return service.getPackagesByPriceRange(min, max);
    }
}
