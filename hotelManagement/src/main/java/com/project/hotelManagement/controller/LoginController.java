package com.project.hotelManagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
    @GetMapping("/login")
    public String login() {
    	logger.info("Start login method");
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
    	logger.info("Start signup method");
        return "signup";
    }
    
    @GetMapping("/signupAdmin")
    public String signupAdmin() {
    	logger.info("Start signupAdmin method");
        return "signupAdmin";
    }
    
    @GetMapping("/welcome")
    public String welcome(Model model) {
    	logger.info("Start welcome method");
    	try {
    		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	     String username = authentication.getName(); 
    	      model.addAttribute("username", username);
    	        
		} catch (Exception e) {
			logger.error("exceptioin in welcome method  "+e);
		}
       
        logger.info("End welcome method");
        return "welcome";
    }
}