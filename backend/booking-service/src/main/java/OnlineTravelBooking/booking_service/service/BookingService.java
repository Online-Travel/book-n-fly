package OnlineTravelBooking.booking_service.service;

import OnlineTravelBooking.booking_service.dto.BookingRequestDTO;
import OnlineTravelBooking.booking_service.dto.UpdateBookingRequestDTO;
import OnlineTravelBooking.booking_service.model.Booking;
import OnlineTravelBooking.booking_service.model.BookingType;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<Booking> getAllBookings();

    Optional<Booking> getBookingById(Long bookingId);

    Booking addBooking(String token, BookingRequestDTO booking);

    Booking updateBooking(Long bookingId, UpdateBookingRequestDTO booking);

    void deleteBooking(Long bookingId);

    Booking getBookingByDetails(BookingType type, Long itemId, Long userId);

    List<Booking> getBookingByUserId(Long userId);

}