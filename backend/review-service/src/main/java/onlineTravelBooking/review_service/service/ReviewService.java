package onlineTravelBooking.review_service.service;

import onlineTravelBooking.review_service.dto.ReviewRequestDTO;
import onlineTravelBooking.review_service.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {

    ReviewResponseDTO addReview(Long userId, ReviewRequestDTO dto);

    List<ReviewResponseDTO> getReviewsByUser(Long userId);

    List<ReviewResponseDTO> getReviewsByHotel(Long hotelId);

    ReviewResponseDTO updateReview(Long userId, Long reviewId, ReviewRequestDTO dto);

    void deleteReview(Long userId, Long reviewId);

    List<ReviewResponseDTO> getAllReviews();
}
