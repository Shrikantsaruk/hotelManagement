package com.project.hotelManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_accounts")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="USER_ID", unique=true, nullable=false)
    private Long id;

    @Column(name="USER_NAME" ,unique=true)
    private String username;
    
    @Column(name="PASSWORD")
    private String password;
    
    @Column(name="EMAIL_ID")
    private String emailId;
    
    @Column(name="USER_ROLE_STATUS")
    private Character userRoleStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Character getUserRoleStatus() {
		return userRoleStatus;
	}

	public void setUserRoleStatus(Character userRoleStatus) {
		this.userRoleStatus = userRoleStatus;
	}


}