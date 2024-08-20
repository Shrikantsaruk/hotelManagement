package com.project.hotelManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.hotelManagement.model.RoomType;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

	@Query(" from RoomType rmt left join rmt.hotel ht where ht.hotelId=?1 ")
	public List<RoomType> findByHotelId(@Param("hotelId") Long hotelId);

}
