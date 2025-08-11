package onlineTravelBooking.review_service.service;

import onlineTravelBooking.review_service.dto.ReviewRequestDTO;
import onlineTravelBooking.review_service.dto.ReviewResponseDTO;
import onlineTravelBooking.review_service.entity.Review;
import onlineTravelBooking.review_service.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository repo;

    @Autowired
    public ReviewServiceImpl(ReviewRepository repo) {
        this.repo = repo;
    }

    @Override
    public ReviewResponseDTO addReview(Long userId, ReviewRequestDTO dto) {
        Review review = new Review();
        review.setUserId(userId);
        review.setHotelId(dto.getHotelId());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setTimestamp(LocalDateTime.now());

        return toDTO(repo.save(review));
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByUser(Long userId) {
        return repo.findByUserId(userId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByHotel(Long hotelId) {
        return repo.findByHotelId(hotelId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ReviewResponseDTO updateReview(Long userId, Long reviewId, ReviewRequestDTO dto) {
        Review review = repo.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));

        if (!review.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this review.");
        }

        // Update review fields here
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());

        repo.save(review);
        return toDTO(review);
    }

    @Override
    public void deleteReview(Long userId, Long reviewId) {
        Review review = repo.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));

        if (!review.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this review.");
        }

        repo.delete(review);
    }

    @Override
    public List<ReviewResponseDTO> getAllReviews() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ReviewResponseDTO toDTO(Review r) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setReviewId(r.getReviewId());
        dto.setUserId(r.getUserId());
        dto.setHotelId(r.getHotelId());
        dto.setRating(r.getRating());
        dto.setComment(r.getComment());
        dto.setTimestamp(r.getTimestamp());
        return dto;
    }
}
