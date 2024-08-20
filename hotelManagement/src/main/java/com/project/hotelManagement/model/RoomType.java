package com.project.hotelManagement.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "room_type")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="room_type_id")
    private Long roomTypeId;

    @Column(name="type")
    private String type;
    
    @Column(name="available_rooms")
    private Integer availableRooms;
    
    @Column(name="price_per_night")
    private BigDecimal pricePerNight;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Transient
    private String amenitiesString;
    
    
	
	 @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL)
	    private List<RoomAmenity> roomAmenities;


	   @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL)
	    private List<Room> rooms = new ArrayList<>();
	   

	public Long getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(Long roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getAvailableRooms() {
		return availableRooms;
	}

	public void setAvailableRooms(Integer availableRooms) {
		this.availableRooms = availableRooms;
	}

	public BigDecimal getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(BigDecimal pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	public String getAmenitiesString() {
		return amenitiesString;
	}

	public void setAmenitiesString(String amenitiesString) {
		this.amenitiesString = amenitiesString;
	}
	
	public List<RoomAmenity> getRoomAmenities() {
		return roomAmenities;
	}

	public void setRoomAmenities(List<RoomAmenity> roomAmenities) {
		this.roomAmenities = roomAmenities;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}


}
