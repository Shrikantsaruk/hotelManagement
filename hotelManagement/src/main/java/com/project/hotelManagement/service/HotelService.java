package com.project.hotelManagement.service;

import java.time.LocalDate;
import java.util.List;

import com.project.hotelManagement.model.Hotel;
import com.project.hotelManagement.model.Room;
import com.project.hotelManagement.model.RoomType;

public interface HotelService {

	Hotel saveHotel(Hotel hotel);
	
    List<Hotel> getAllHotels();
    
    Hotel getHotelById(Long id);


	RoomType getByRoomTypeId(Long id);

	List<Room> getRoomByRoomTypeId(Long id);

	void updateHotel(Hotel hotel);

	Room getRoomById(Long roomId);

	void updateRoom(Room room);

	void deleteHotel(Long id) throws Exception ;

}
