package com.project.hotelManagement.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="amenity_id")
    private Long amenityId;

    @Column(name="amenity_name")
    private String amenityName;

    @OneToMany(mappedBy = "amenity", cascade = CascadeType.ALL)
    private List<RoomAmenity> roomAmenities;
    
	public Long getAmenityId() {
		return amenityId;
	}

	public void setAmenityId(Long amenityId) {
		this.amenityId = amenityId;
	}

	public String getAmenityName() {
		return amenityName;
	}

	public void setAmenityName(String amenityName) {
		this.amenityName = amenityName;
	}


	public List<RoomAmenity> getRoomAmenities() {
		return roomAmenities;
	}

	public void setRoomAmenities(List<RoomAmenity> roomAmenities) {
		this.roomAmenities = roomAmenities;
	}

   
}