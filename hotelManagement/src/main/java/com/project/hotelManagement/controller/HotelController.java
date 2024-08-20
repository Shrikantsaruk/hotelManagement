package com.project.hotelManagement.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.hotelManagement.model.Amenity;
import com.project.hotelManagement.model.Hotel;
import com.project.hotelManagement.model.Room;
import com.project.hotelManagement.model.RoomAmenity;
import com.project.hotelManagement.model.RoomType;
import com.project.hotelManagement.model.User;
import com.project.hotelManagement.repository.AmenityRepository;
import com.project.hotelManagement.service.HotelService;
import com.project.hotelManagement.service.UserService;

@Controller
public class HotelController {

	private static final Logger logger = LoggerFactory.getLogger(HotelController.class);	
	
    @Autowired
    private HotelService hotelService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private AmenityRepository amenityRepository;
     
    @GetMapping("/create")
    public ModelAndView showCreateHotelForm(ModelAndView modelAndView) {
    	logger.info("Start showCreateHotelForm method");
    	try {
    		modelAndView.addObject("hotel", new Hotel());
       	 User user=userService.getCurrentUser();
        	modelAndView.addObject("user", user);
       	modelAndView.setViewName("create-hotel");
       	
		} catch (Exception e) {
			logger.error("Exception showCreateHotelForm method "+e);
		}
    	
    	logger.info("End showCreateHotelForm method");
        return modelAndView;
    }

    @PostMapping("/saveHotel")
    public String saveHotel(@ModelAttribute("hotel") Hotel hotel) {
    	
    	logger.info("Start saveHotel method");
    	try {
    		for (RoomType roomType : hotel.getRoomTypes()) {
                String amenitiesString = roomType.getAmenitiesString();

                List<RoomAmenity> roomAmenityList = new ArrayList<>();

                for (String name : amenitiesString.split(",")) {
                    String trimmedName = name.trim();
                    Amenity amenity = amenityRepository.findByAmenityName(trimmedName);

                    if (amenity == null) {
                        amenity = createAndSaveAmenity(trimmedName);
                    }

                    RoomAmenity roomAmenity = new RoomAmenity();
                    roomAmenity.setRoomType(roomType);
                    roomAmenity.setAmenity(amenity);

                    roomAmenityList.add(roomAmenity);
                }
                roomType.setRoomAmenities(roomAmenityList);

                for (int i = 0; i < roomType.getAvailableRooms(); i++) {
                    Room room = new Room();
                    room.setRoomNumber("Room-" + (i + 1)); 
                    room.setRoomType(roomType);
                    room.setPricePerNight(roomType.getPricePerNight());
                    roomType.getRooms().add(room); 
                }
            }

            hotelService.saveHotel(hotel);
		} catch (Exception e) {
			logger.error("Exception saveHotel method "+e);
		}
              
        logger.info("End saveHotel method");
        return "redirect:/list";
    }

    @GetMapping("/list")
    public ModelAndView listHotels(ModelAndView modelAndView) {
    	logger.info("Start listHotels method");
    	try {
    		 List<Hotel> hotels = hotelService.getAllHotels();
    	        modelAndView.addObject("hotels", hotels);
    	        User user=userService.getCurrentUser();
    	    	modelAndView.addObject("user", user);
    	    	
    	    	if(user.getUserRoleStatus()!=null
    	    		&&	user.getUserRoleStatus()=='A') {
    	    		modelAndView.setViewName("hotel-adminlist");
    	    	}else
    	    		modelAndView.setViewName("hotel-list");
		} catch (Exception e) {
			logger.error("Exception listHotels method "+e);
		}
           	
    	logger.info("End listHotels method");
        return modelAndView;
    }
    
    @GetMapping("/view/{id}")
    public ModelAndView viewHotel(@PathVariable("id") Long id, ModelAndView modelAndView) {
    	logger.info("Start viewHotel method");
    	try {

        	User user=userService.getCurrentUser();
        	modelAndView.addObject("user", user);
            Hotel hotel = hotelService.getHotelById(id);
            modelAndView.addObject("hotel", hotel);
            
            List<RoomType> roomTypes = hotel.getRoomTypes();
           
            for(RoomType roomType: roomTypes) {
            	String roomTypeAmenitiesString = roomType.getRoomAmenities().stream()
                	    .map(roomAmenity -> roomAmenity.getAmenity().getAmenityName())
                	    .collect(Collectors.joining(", "));
            	roomType.setAmenitiesString(roomTypeAmenitiesString);
            }
            
            
            modelAndView.addObject("roomTypes", roomTypes);
            
            modelAndView.setViewName("hotel-view");
            
		} catch (Exception e) {
			logger.error("Exception viewHotel method "+e );
		}
        logger.info("End viewHotel method");
        return modelAndView;
    }
    
    private Amenity createAndSaveAmenity(String name) {
    	logger.info("Start createAndSaveAmenity method");
    	Amenity amenity=null;
    	try {
    		amenity = new Amenity();
            
            amenity.setAmenityName(name);
            amenity=amenityRepository.save(amenity);
		} catch (Exception e) {
			logger.error("Exception createAndSaveAmenity method "+e);	
		}
    	logger.info("End createAndSaveAmenity method");
       return amenity;        
    }
    
    @GetMapping("/room/view/{id}")
    public ModelAndView viewRoomDetails(@PathVariable("id") Long id, ModelAndView modelAndView) {
    	logger.info("Start viewRoomDetails method");
    	try {
    		User user=userService.getCurrentUser();
        	modelAndView.addObject("user", user);
        	
            RoomType roomType = hotelService.getByRoomTypeId(id);
          
            Hotel Hotel = hotelService.getHotelById(roomType.getHotel().getHotelId());
            modelAndView.addObject("hotel", Hotel);
            
            List<Room> rooms= hotelService.getRoomByRoomTypeId(id);
     
            String roomTypeAmenitiesString = roomType.getRoomAmenities().stream()
            	    .map(roomAmenity -> roomAmenity.getAmenity().getAmenityName())
            	    .collect(Collectors.joining(", "));
            modelAndView.addObject("roomTypeAmenitiesString", roomTypeAmenitiesString);
            
            modelAndView.addObject("roomType", roomType);
            for(Room room :rooms) {
    		       List roomAmenitys= room.getRoomAmenities().stream()
    						          .map(RoomAmenity::getAmenity)
    						          .collect(Collectors.toList());
    		       room.setRoomAmenities(roomAmenitys);
            }
            modelAndView.addObject("rooms", rooms);
            modelAndView.setViewName("room-details");
            
		} catch (Exception e) {
			logger.error("Exception viewRoomDetails method "+e);
		}
    	
        logger.info("End viewRoomDetails method");
        return modelAndView;
    }
    
    
    @GetMapping("/editHotelDetails/{id}")
    public ModelAndView editAllDetailsForm(@PathVariable("id") Long id, ModelAndView modelAndView) {
    
    	logger.info("Start editAllDetailsForm method");
    	try {
    		Hotel hotel = hotelService.getHotelById(id);
            for (RoomType roomType : hotel.getRoomTypes()) {
                String amenitiesString = roomType.getRoomAmenities().stream()
                    .map(amenity -> amenity.getAmenity().getAmenityName())
                    .collect(Collectors.joining(", "));
                roomType.setAmenitiesString(amenitiesString);
            }
            User user=userService.getCurrentUser();
        	modelAndView.addObject("user", user);
            modelAndView.addObject("hotel", hotel);
            modelAndView.setViewName("hotelroom-edit");
            
		} catch (Exception e) {
			 logger.error("Exception editAllDetailsForm method "+e );
		}
    	
        logger.info("End editAllDetailsForm method");
        return modelAndView;
    }

    @PostMapping("/updateHotelDetails")
    public String updateAllDetails(@ModelAttribute Hotel hotel) {
    	 logger.info("Start updateAllDetails method");
    	try {
    		hotelService.updateHotel(hotel);
		} catch (Exception e) {
			logger.error("Exception updateAllDetails method "+e);
		}
        
    	 logger.info("End updateAllDetails method");
        return "redirect:/list";
    }
    
    @GetMapping("/editRoomDetails/{id}")
    public ModelAndView showEditRoomForm(@PathVariable("id") Long roomId, ModelAndView modelAndView) {
    	logger.info("End updateAllDetails method");
    	try {
    		User user=userService.getCurrentUser();
         	modelAndView.addObject("user", user);
         	
        	Room room = hotelService.getRoomById(roomId);
            room.setAmenitiesString(room.getRoomAmenities().stream()
                    .map(RoomAmenity::getAmenity)
                    .map(Amenity::getAmenityName)
                    .collect(Collectors.joining(", ")));
            modelAndView.addObject("room", room); 
            
            RoomType roomType = hotelService.getByRoomTypeId(room.getRoomType().getRoomTypeId());
            
            Hotel Hotel = hotelService.getHotelById(roomType.getHotel().getHotelId());
            modelAndView.addObject("hotel", Hotel);        
            modelAndView.setViewName("room-edit");
            
		} catch (Exception e) {
			 logger.error("Exception updateAllDetails method "+e);
		}    	
        logger.info("End updateAllDetails method");
        return modelAndView;
    }

    @PostMapping("/updateRoomDetails")
    public String updateRoomDetails(@ModelAttribute Room room) {
    	logger.info("Start updateRoomDetails method");
    	try {
    		hotelService.updateRoom(room);
		} catch (Exception e) {
			logger.error("Exception updateRoomDetails method "+e);
		}    	
    	logger.info("End updateRoomDetails method");
        return "redirect:/list";
    }
    
    @PostMapping("/admin/deleteHotels")
    public String deleteHotels(@RequestParam("hotelIds") String hotelIdsParam, RedirectAttributes redirectAttributes) {
    	logger.info("Start deleteHotels method");
    	try {
            String[] hotelIdsArray = hotelIdsParam.split(",");
            Set<String> hotelIdsSet = new HashSet<>(Arrays.asList(hotelIdsArray));
            
            List<Long> hotelIds = hotelIdsSet.stream().map(Long::parseLong).collect(Collectors.toList());
            for (Long hotelId : hotelIds) {
                hotelService.deleteHotel(hotelId);
            }
            redirectAttributes.addFlashAttribute("successMessage", "Selected hotels deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
    	logger.info("End deleteHotels method");
        return "redirect:/list";
    } 
    
}