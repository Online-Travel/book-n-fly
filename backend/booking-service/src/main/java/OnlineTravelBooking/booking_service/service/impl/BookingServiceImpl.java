package OnlineTravelBooking.booking_service.service.impl;

import OnlineTravelBooking.booking_service.dto.BookingRequestDTO;
import OnlineTravelBooking.booking_service.dto.UpdateBookingRequestDTO;
import OnlineTravelBooking.booking_service.model.Booking;
import OnlineTravelBooking.booking_service.repository.BookingRepository;
import OnlineTravelBooking.booking_service.service.BookingService;
import OnlineTravelBooking.booking_service.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public List<Booking> getAllBookings() {

        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> getBookingById(Long bookingId) {

        return bookingRepository.findById(bookingId);
    }

    @Override
    public Booking addBooking(String token, BookingRequestDTO booking) {

        Booking book=new Booking();
        book.setUserId(jwtUtil.extractUserId(token));
        book.setType(booking.getType());
        book.setStatus("Pending");

        return bookingRepository.save(book);
    }

    @Override
    public Booking updateBooking(Long bookingId, UpdateBookingRequestDTO booking) {
        Optional<Booking> saved=bookingRepository.findById(bookingId);
        Booking book=saved.get();
        book.setStatus(booking.getStatus());
        book.setPaymentId(booking.getPaymentId());
        return bookingRepository.save(book);
    }

    @Override
    public void deleteBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}
