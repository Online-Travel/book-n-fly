package OnlineTravelBooking.booking_service.service;

import OnlineTravelBooking.booking_service.model.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelService {

    List<Hotel> getAllHotels();

    Optional<Hotel> getHotelById(Long hotelId);

    Hotel addHotel(Hotel hotel);

    Hotel updateHotel(Long hotelId, Hotel hotel);

    void deleteHotel(Long hotelId);

    List<Hotel> searchHotels(String location, Double minRating, Double maxRating, Double minPrice, Double maxPrice, Integer minRooms, Integer maxRooms);
}
