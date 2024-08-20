package com.project.hotelManagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.hotelManagement.model.Booking;
import com.project.hotelManagement.model.Hotel;
import com.project.hotelManagement.model.Room;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Room room, LocalDate startDate, LocalDate endDate);

	 List<Booking> findByUserId(Long userId);

	 @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END FROM Booking b WHERE b.hotel.hotelId = :hotelId")
	 boolean existsByHotelId(@Param("hotelId") Long hotelId);
	  
}
