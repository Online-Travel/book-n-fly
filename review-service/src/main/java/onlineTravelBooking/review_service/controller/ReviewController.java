package onlineTravelBooking.review_service.controller;

import jakarta.validation.Valid;
import onlineTravelBooking.review_service.dto.ReviewRequestDTO;
import onlineTravelBooking.review_service.dto.ReviewResponseDTO;
import onlineTravelBooking.review_service.service.ReviewService;
import onlineTravelBooking.review_service.service.UserClient;
import onlineTravelBooking.review_service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserClient userClient;
    private final JwtUtil jwtUtil;

    @Autowired
    public ReviewController(ReviewService reviewService, UserClient userClient, JwtUtil jwtUtil) {
        this.reviewService = reviewService;
        this.userClient = userClient;
        this.jwtUtil = jwtUtil;
    }

    // Add review - Only accessible by TRAVELER
    @PostMapping
    @PreAuthorize("hasRole('TRAVELER')")
    public ReviewResponseDTO addReview(@RequestHeader("Authorization") String token,
                                       @Valid @RequestBody ReviewRequestDTO dto) {
        try{
            Long userId = jwtUtil.extractUserId(token);
            System.out.println("Extracted userId in cont"+userId);
            return reviewService.addReview(userId, dto);
        }
        catch (Exception e){
            System.out.println("Error in addReview controller"+e.getMessage());
            e.printStackTrace();
            throw e;
        }

    }

    // Get logged-in user's reviews - TRAVELER only
    @GetMapping("/my")
    @PreAuthorize("hasRole('TRAVELER')")
    public List<ReviewResponseDTO> getMyReviews(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.extractUserId(token);
        return reviewService.getReviewsByUser(userId);
    }

    // Get all reviews for a specific hotel - accessible to all authenticated users
    @GetMapping("/hotel/{hotelId}")
    @PreAuthorize("isAuthenticated()")
    public List<ReviewResponseDTO> getHotelReviews(@PathVariable Long hotelId) {
        return reviewService.getReviewsByHotel(hotelId);
    }

    // Update review - TRAVELER only
    @PutMapping("/{reviewId}")
    @PreAuthorize("hasRole('TRAVELER')")
    public ReviewResponseDTO updateReview(@RequestHeader("Authorization") String token,
                                          @PathVariable Long reviewId,
                                          @Valid @RequestBody ReviewRequestDTO dto) {
        Long userId = jwtUtil.extractUserId(token);
        return reviewService.updateReview(userId, reviewId, dto);
    }



    // Delete review - TRAVELER only
    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasRole('TRAVELER')")
    public void deleteReview(@RequestHeader("Authorization") String token,
                             @PathVariable Long reviewId) {
        Long userId = jwtUtil.extractUserId(token);
        reviewService.deleteReview(userId, reviewId);
    }

    // Admin can view all reviews
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ReviewResponseDTO> getAllReviews() {
        System.out.println("Admin arrived");
        return reviewService.getAllReviews();
    }
}
