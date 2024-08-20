package com.project.hotelManagement.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.hotelManagement.controller.HotelController;
import com.project.hotelManagement.model.Booking;
import com.project.hotelManagement.model.Hotel;
import com.project.hotelManagement.model.Room;
import com.project.hotelManagement.model.User;
import com.project.hotelManagement.repository.BookingRepository;
import com.project.hotelManagement.repository.RoomRepository;
import com.project.hotelManagement.repository.UserRepository;

@Service
public class BookingService {

	private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
	
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking createBooking(Long roomId, Long userId, LocalDate startDate, LocalDate endDate) throws Exception {
    	logger.info("Start createBooking method");

    	Room room = roomRepository.findById(roomId).orElseThrow(() -> new Exception("Room not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
       
        List<Booking> conflictingBookings = bookingRepository.findByRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                room, endDate, startDate);

        if (!conflictingBookings.isEmpty()) {
            throw new Exception("Room is already booked for the given dates");
        }

        Booking booking = new Booking();
        Hotel hotel = room.getRoomType().getHotel();
        booking.setRoom(room);
        booking.setUser(user);
        booking.setHotel(hotel);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setStatus("CONFIRMED");
        logger.info("End createBooking method");
        return bookingRepository.save(booking);
    }
    
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public void cancelBooking(Long bookingId) throws Exception {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new Exception("Booking not found"));
        bookingRepository.delete(booking);
    }

    public List<Booking> getAllBooking(){
    	return bookingRepository.findAll();
    }
    
}