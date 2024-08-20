package com.project.hotelManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.hotelManagement.model.Amenity;
import com.project.hotelManagement.model.Room;
import com.project.hotelManagement.model.RoomAmenity;
import com.project.hotelManagement.model.RoomType;

import jakarta.transaction.Transactional;

public interface RoomAmenityRepository extends JpaRepository<RoomAmenity, Long> {

	RoomAmenity findByRoomTypeAndAmenity(RoomType roomType, Amenity amenity);
	
	 	@Modifying
	    @Transactional
	    @Query("DELETE FROM RoomAmenity ra WHERE ra.roomType = :roomType")
	    void deleteByRoomType(@Param("roomType") RoomType roomType);
	 	
	    RoomAmenity findByRoomAndAmenity(Room room, Amenity amenity);


}
