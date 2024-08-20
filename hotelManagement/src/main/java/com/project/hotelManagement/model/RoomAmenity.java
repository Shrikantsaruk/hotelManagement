package com.project.hotelManagement.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "room_amenity")
public class RoomAmenity {

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name="room_amenity_id")
	    private Long roomAmenityId;

	    @ManyToOne
	    @JoinColumn(name = "room_type_id")
	    private RoomType roomType;

	    @ManyToOne
	    @JoinColumn(name = "amenity_id")
	    private Amenity amenity;
	    
	    @ManyToOne
	    @JoinColumn(name = "room_id")
	    private Room room;

		public Long getRoomAmenityId() {
			return roomAmenityId;
		}

		public void setRoomAmenityId(Long roomAmenityId) {
			this.roomAmenityId = roomAmenityId;
		}

		public RoomType getRoomType() {
			return roomType;
		}

		public void setRoomType(RoomType roomType) {
			this.roomType = roomType;
		}

		public Amenity getAmenity() {
			return amenity;
		}

		public void setAmenity(Amenity amenity) {
			this.amenity = amenity;
		}

		public Room getRoom() {
			return room;
		}

		public void setRoom(Room room) {
			this.room = room;
		}
	    
	    
}
