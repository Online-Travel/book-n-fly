package onlineTravelBooking.review_service.repository;

import onlineTravelBooking.review_service.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import feign.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    List<Review> findByHotelId(Long hotelId);
    List<Review> findByUserId(Long userId);
    Optional<Review> findByReviewIdAndUserId(Long reviewId, Long userId);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.hotelId = :hotelId")
    Double findAverageRatingByHotelId(@Param("hotelId") Long hotelId);
}
