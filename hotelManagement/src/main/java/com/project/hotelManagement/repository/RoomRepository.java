package com.project.hotelManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.hotelManagement.model.Amenity;
import com.project.hotelManagement.model.Room;
import com.project.hotelManagement.model.RoomAmenity;
import com.project.hotelManagement.model.RoomType;

public interface RoomRepository extends JpaRepository<Room, Long> {

	@Query(" FROM Room rm LEFT JOIN  rm.roomType rmt WHERE rmt.roomTypeId = :roomTypeId")
	public List<Room> findAllByRoomTypeId(@Param("roomTypeId") Long roomTypeId);

	@Query("SELECT ra FROM RoomAmenity ra WHERE ra.roomType = :roomType AND ra.amenity = :amenity")
	RoomAmenity findByRoomTypeAndAmenity(@Param("roomType") RoomType roomType, @Param("amenity") Amenity amenity);

	public Room findByRoomNumberAndRoomType(String roomNumber, RoomType roomType);

	
  
}
