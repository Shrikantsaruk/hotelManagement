package com.project.hotelManagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.hotelManagement.model.User;
import com.project.hotelManagement.service.UserService;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String saveUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
    	logger.info("Start signup method");
    	try {
    		 if (userService.findByUsername(user.getUsername()) != null) {
                 redirectAttributes.addFlashAttribute("error", "Username already exists");
                 return "redirect:/signup";
             }
        	userService.save(user, redirectAttributes);
        	 redirectAttributes.addFlashAttribute("message", "User created successfully");
        	 
		} catch (Exception e) {
			logger.error("exception in signup method  "+e );
		}
    	
    	 logger.info("End signup method");
        return "redirect:/login"; 
    }
    
    @RequestMapping("/signup")
    public String showSignupForm(Model model) {
    	logger.info("Start showSignupForm method");
        return "signup";
    }
    
    @RequestMapping("/signupAdmin")
    public String showSignupAdminForm(Model model) {
    	logger.info("Start showSignupForm method");
        return "signupAdmin";
    }
    
    @PostMapping("/signupAdmin")
    public String saveAdmin(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
    	logger.info("Start signup method");
    	try {
    		 if (userService.findByUsername(user.getUsername()) != null) {
                 redirectAttributes.addFlashAttribute("error", "Username already exists");
                 return "redirect:/signup";
             }
        	userService.saveAdmin(user, redirectAttributes);
        	 redirectAttributes.addFlashAttribute("message", "Admin User created successfully");
        	 
		} catch (Exception e) {
			logger.error("exception in signup method  "+e );
		}
    	
    	 logger.info("End signup method");
        return "redirect:/login"; 
    }
}
