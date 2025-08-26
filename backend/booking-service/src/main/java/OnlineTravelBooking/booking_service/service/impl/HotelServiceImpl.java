package OnlineTravelBooking.booking_service.service.impl;

import OnlineTravelBooking.booking_service.model.Hotel;
import OnlineTravelBooking.booking_service.repository.HotelRepository;
import OnlineTravelBooking.booking_service.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {

        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> getAllHotels() {

        return hotelRepository.findAll();
    }

    @Override
    public Optional<Hotel> getHotelById(Long hotelId) {

        return hotelRepository.findById(hotelId);
    }

    @Override
    public Hotel addHotel(Hotel hotel) {

        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel updateHotel(Long hotelId, Hotel hotel) {
        hotel.setHotelId(hotelId);  // Set the ID for updating
        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(Long hotelId) {
        hotelRepository.deleteById(hotelId);
    }

    @Override
    public List<Hotel> searchHotels(String location, Double minRating, Double maxPrice, Integer minRooms) {
        return hotelRepository.searchHotels(location, minRating, maxPrice, minRooms);
    }
}
