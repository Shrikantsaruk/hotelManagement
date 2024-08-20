package com.project.hotelManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.hotelManagement.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

	  @Query("SELECT DISTINCT ht FROM Hotel ht LEFT JOIN FETCH ht.roomTypes rmt WHERE rmt.roomTypeId = :roomTypeId")
	    public Hotel findByRoomTypeId(@Param("roomTypeId") Long roomTypeId);
}
