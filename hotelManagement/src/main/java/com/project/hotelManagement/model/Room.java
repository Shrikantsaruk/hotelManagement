package com.project.hotelManagement.model;

import java.math.BigDecimal;
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
@Table(name = "room")
public class Room {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name="room_id")
	    private Long roomId;

	    @Column(name="room_number")
	    private String roomNumber;

	    @Column(name="price_per_night")
	    private BigDecimal pricePerNight;
	    
	    @Transient
	    private String amenitiesString;
	    
	    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
	    private List<RoomAmenity> roomAmenities;

	    
	    @ManyToOne
	    @JoinColumn(name = "room_type_id")
	    private RoomType roomType;

	    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL ,orphanRemoval = true)
	    private List<Booking> bookings;
	    
		public Long getRoomId() {
			return roomId;
		}


		public void setRoomId(Long roomId) {
			this.roomId = roomId;
		}


		public String getRoomNumber() {
			return roomNumber;
		}


		public void setRoomNumber(String roomNumber) {
			this.roomNumber = roomNumber;
		}


		public BigDecimal getPricePerNight() {
			return pricePerNight;
		}


		public void setPricePerNight(BigDecimal pricePerNight) {
			this.pricePerNight = pricePerNight;
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


		public RoomType getRoomType() {
			return roomType;
		}


		public void setRoomType(RoomType roomType) {
			this.roomType = roomType;
		}


		public List<Booking> getBookings() {
			return bookings;
		}


		public void setBookings(List<Booking> bookings) {
			this.bookings = bookings;
		}
	    

}
