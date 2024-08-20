package com.project.hotelManagement.repository;
import com.project.hotelManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
	User findByUsername(String username);
	
}