package OnlineTravelBooking.package_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import OnlineTravelBooking.package_service.model.TravelPackage;
import OnlineTravelBooking.package_service.service.TravelPackageService;
import OnlineTravelBooking.package_service.utils.JwtUtil;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/packages")
public class TravelPackageController {

	private final TravelPackageService service;
	private final JwtUtil jwtUtil;

	public TravelPackageController(TravelPackageService service, JwtUtil jwtUtil) {
	    this.service = service;
	    this.jwtUtil = jwtUtil;
	}


    @PostMapping
    @PreAuthorize("hasRole('TRAVEL_AGENT')")
    public TravelPackage create(@RequestHeader("Authorization") String token, @RequestBody TravelPackage pkg) {
        return service.savePackage(token.substring(7).trim(), pkg);
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
    @PreAuthorize("hasAnyRole('TRAVEL_AGENT','ADMIN')")
    public TravelPackage update(@PathVariable Long id, @RequestBody TravelPackage pkg) {
        return service.updatePackage(id, pkg);
    }

    @PatchMapping("/{id}/inactive")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENT','ADMIN')")
    public ResponseEntity<TravelPackage> makeInactive(@PathVariable Long id) {
        TravelPackage updated = service.makePackageInactive(id);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/active")
    @PreAuthorize("hasAnyRole('TRAVEL_AGENT','ADMIN')")
    public ResponseEntity<TravelPackage> makeActive(@PathVariable Long id) {
        TravelPackage updated = service.makePackageActive(id);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
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
    
    @GetMapping("/my-packages")
    @PreAuthorize("hasRole('TRAVEL_AGENT')")
    public List<TravelPackage> getMyPackages(@RequestHeader("Authorization") String token) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7).trim() : token;
        Long agentId = jwtUtil.extractUserId(jwt);
        return service.getPackagesByAgent(agentId);
    }
}
