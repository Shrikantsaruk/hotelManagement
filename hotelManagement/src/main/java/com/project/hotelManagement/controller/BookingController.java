package com.project.hotelManagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.hotelManagement.model.Booking;
import com.project.hotelManagement.model.Room;
import com.project.hotelManagement.model.User;
import com.project.hotelManagement.service.BookingService;
import com.project.hotelManagement.service.HotelService;
import com.project.hotelManagement.service.UserService;

@Controller
public class BookingController {

	private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
	
		@Autowired
	    private BookingService bookingService;

	    @Autowired
	    private UserService userService;
	    
	    @Autowired
	    private HotelService hotelService;

	    @PostMapping("/bookRoom")
	    public String bookRoom(@RequestParam Long roomId,
	                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
	                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
	                           RedirectAttributes redirectAttributes) {
	    	Room room=null;
	    	logger.info("Start bookRoom method");
	    	try {
	        	 room = hotelService.getRoomById(roomId);
	        	User user=userService.getCurrentUser();
	            Booking booking = bookingService.createBooking(roomId, user.getId(), startDate, endDate);
	            redirectAttributes.addFlashAttribute("successMessage", "Booking successful!");
	        } catch (Exception e) {
	        	 redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
	        	 logger.error("Exception bookRoom method"+e);
	        }
	    	logger.info("End bookRoom method");
	        return "redirect:/room/view/" + room.getRoomType().getRoomTypeId();
	    }
	    
	    @GetMapping("/cart")
	    public String viewCart(Model model) {
	    	logger.info("Start viewCart method");
	    	try {
	    		User user=userService.getCurrentUser();
		    	model.addAttribute("user", user);
		     	
		        List<Booking> bookings = bookingService.getBookingsByUser(user.getId());
		        model.addAttribute("bookings", bookings);
		        
			} catch (Exception e) {
				 logger.error("Exception bookRoom method"+e);
			}
	    	logger.info("End viewCart method");
	        return "cart";
	    }
	    
	    @GetMapping("/allcart")
	    public String viewAllCart(Model model) {
	    	logger.info("Start viewAllCart method");
	    	try {
	    		User user=userService.getCurrentUser();
		    	model.addAttribute("user", user);
		        List<Booking> bookings = bookingService.getAllBooking();
		        model.addAttribute("bookings", bookings);
			} catch (Exception e) {
				logger.error("Exception viewAllCart method "+e);
			}
	    	logger.info("End viewAllCart method");
	        return "admin-cart";
	        
	    }

	    @PostMapping("/cancelBooking")
	    public String cancelBooking(@RequestParam Long bookingId, RedirectAttributes redirectAttributes) {
	    	logger.info("Start cancelBooking method");
	    	try {
	            bookingService.cancelBooking(bookingId);
	            redirectAttributes.addFlashAttribute("successMessage", "Booking canceled successfully!");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
	            logger.error("Exception cancelBooking method "+e);
	        }
	    	logger.info("End cancelBooking method");
	        return "redirect:/cart";
	    }
}
