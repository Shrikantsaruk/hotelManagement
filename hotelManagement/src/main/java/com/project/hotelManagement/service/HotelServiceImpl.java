package com.project.hotelManagement.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.hotelManagement.controller.HotelController;
import com.project.hotelManagement.model.Amenity;
import com.project.hotelManagement.model.Hotel;
import com.project.hotelManagement.model.Room;
import com.project.hotelManagement.model.RoomAmenity;
import com.project.hotelManagement.model.RoomType;
import com.project.hotelManagement.repository.AmenityRepository;
import com.project.hotelManagement.repository.BookingRepository;
import com.project.hotelManagement.repository.HotelRepository;
import com.project.hotelManagement.repository.RoomAmenityRepository;
import com.project.hotelManagement.repository.RoomRepository;
import com.project.hotelManagement.repository.RoomTypeRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

	private static final Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);
	
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private AmenityRepository amenityRepository;
    
    @Autowired
    private RoomAmenityRepository roomAmenityRepository;

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Override
    public Hotel saveHotel(Hotel hotel) {
    	logger.info("Start saveHotel method");
    	try {
    		hotelRepository.save(hotel);
            for (RoomType roomType : hotel.getRoomTypes()) {
                for (RoomAmenity roomAmenity : roomType.getRoomAmenities()) {
                    if (roomAmenity.getAmenity().getAmenityId() == null) {
                        amenityRepository.save(roomAmenity.getAmenity());
                    }
                    roomAmenityRepository.save(roomAmenity);
                }

                roomType.setHotel(hotel);
                roomType=roomTypeRepository.save(roomType);
                
                for (Room room : roomType.getRooms()) {
                	 
                         room.setRoomType(roomType);
                     
                    roomRepository.save(room);
                }            
            }

		} catch (Exception e) {
			logger.error("Exception saveHotel method "+e);
		}
    	
        logger.info("End saveHotel method");
        return hotel;
    }
    
    @Override
    public void updateHotel(Hotel hotel) {
    	logger.info("Start updateHotel method");
    	try {
    		 hotelRepository.save(hotel);
    	        for (RoomType roomType : hotel.getRoomTypes()) {
    	        	 roomAmenityRepository.deleteByRoomType(roomType);
    	             List<RoomAmenity> roomAmenityList = new ArrayList<>();
    	            for (String name : roomType.getAmenitiesString().split(",")) {
    	                String trimmedName = name.trim();
    	                Amenity amenity = amenityRepository.findByAmenityName(trimmedName);

    	                if (amenity == null) {
    	                    amenity = createAndSaveAmenity(trimmedName);
    	                }

    	                RoomAmenity roomAmenity = roomAmenityRepository.findByRoomTypeAndAmenity(roomType, amenity);
    	                if (roomAmenity == null) {
    	                    roomAmenity = new RoomAmenity();
    	                    roomAmenity.setRoomType(roomType);
    	                    roomAmenity.setAmenity(amenity);
    	                }
    	                roomAmenityList.add(roomAmenity);
    	            }
    	            roomType.setRoomAmenities(roomAmenityList);

    	            roomType.setHotel(hotel);
    	            roomType = roomTypeRepository.save(roomType);

    	            Set<String> existingRoomNumbers = getRoomByRoomTypeId(roomType.getRoomTypeId()).stream()
    	                .map(Room::getRoomNumber)
    	                .collect(Collectors.toSet());

    	            Set<String> updatedRoomNumbers = new HashSet<>();
    	            for (int i = 0; i < roomType.getAvailableRooms(); i++) {
    	                String roomNumber = "Room-" + (i + 1);
    	                Room room = roomRepository.findByRoomNumberAndRoomType(roomNumber, roomType);

    	                if (room == null) {
    	                    room = new Room();
    	                    room.setRoomNumber(roomNumber);
    	                    room.setRoomType(roomType);
    	                }

    	                room.setPricePerNight(roomType.getPricePerNight());
    	                roomRepository.save(room);
    	                updatedRoomNumbers.add(roomNumber);
    	            }
    	            existingRoomNumbers.removeAll(updatedRoomNumbers);
    	            for (String oldRoomNumber : existingRoomNumbers) {
    	                Room roomToRemove = roomRepository.findByRoomNumberAndRoomType(oldRoomNumber, roomType);
    	                if (roomToRemove != null) {
    	                    roomRepository.delete(roomToRemove);
    	                }
    	            }
    	        }
    	        
		} catch (Exception e) {
			logger.error("error updateHotel method "+e);
		}
    	logger.info("End updateHotel method");
    }

    
    public Amenity createAndSaveAmenity(String amenityName) {
    	logger.info("Start createAndSaveAmenity method");
    	Amenity amenity = null;
    	try {
    		 amenity = new Amenity();
    	     amenity.setAmenityName(amenityName);
    	     amenity = amenityRepository.save(amenity);
		} catch (Exception e) {
			logger.error("Excdeption createAndSaveAmenity method "+e);
		}   
    	logger.info("End createAndSaveAmenity method");
         return amenity;        
    }
    
    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotelById(Long id) {
    	logger.info("Start getHotelById method");
    	Hotel hotel =null;
    	try {
    		 hotel = hotelRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Hotel not found"));
        	
        	List<RoomType>roomTypes = roomTypeRepository.findByHotelId(id);
        	
        	hotel.setRoomTypes(roomTypes);
		} catch (Exception e) {
			logger.info("Exception getHotelById method "+e);
		}    	
    	logger.info("End getHotelById method");
        return hotel;
    }
    
   
    
    @Override
    public  RoomType getByRoomTypeId(Long id) {
    	return roomTypeRepository.findById(id)
    			.orElseThrow(() -> new RuntimeException("RoomType not found")); 
    }
    
    @Override
    public List<Room> getRoomByRoomTypeId(Long id){
    	List<Room> rooms = roomRepository.findAllByRoomTypeId(id);
    	return rooms;
    }
    
    public void updateRoom(Room room) {
    	logger.info("Start updateRoom method");
    	try {

        	Room roomTemp =getRoomById(room.getRoomId());
            List<RoomAmenity> roomAmenityList = new ArrayList<>();
            for (String name : room.getAmenitiesString().split(",")) {
                String trimmedName = name.trim();
                Amenity amenity = amenityRepository.findByAmenityName(trimmedName);
                
                if (amenity == null) {
                    amenity = createAndSaveAmenity(trimmedName);
                }
                
                RoomAmenity roomAmenity = roomAmenityRepository.findByRoomAndAmenity(room, amenity);
                if (roomAmenity == null) {
                    roomAmenity = new RoomAmenity();
                    roomAmenity.setRoom(room);
                    roomAmenity.setAmenity(amenity);
                } else {
                    roomAmenityRepository.deleteById(roomAmenity.getRoomAmenityId());
                }
                roomAmenityList.add(roomAmenity);
            }
            room.setRoomAmenities(roomAmenityList);

            room.setRoomType(roomTemp.getRoomType());
            roomRepository.save(room);
		} catch (Exception e) {
			 logger.error("End updateRoom method");
		}
        logger.info("End updateRoom method");
    }
    
    @Override
    public Room getRoomById(Long roomId) {
    	 return roomRepository.findById(roomId)
                 .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomId));
    }
    
    @Override
    public void deleteHotel(Long hotelId) throws Exception {
    	logger.info("Start deleteHotel method");
      	if (bookingRepository.existsByHotelId(hotelId)) {
			    throw new Exception("Hotel cannot be deleted as there are existing bookings.");
			}else {
				 hotelRepository.deleteById(hotelId);
			}
      	logger.info("End deleteHotel method");
    }

	
   
}
