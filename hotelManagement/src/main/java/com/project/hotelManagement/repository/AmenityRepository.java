package com.project.hotelManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hotelManagement.model.Amenity;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {

	 Amenity findByAmenityName(String name);
}
