package com.vestimony.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User_Account")
public class ApplicationUser {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "UserID", unique=true)
	private long userId;
	@Column(name = "Username", unique=true, nullable=false)
	private String username;
	@Column(name = "password", nullable=false)
	private String password;
	@Column(name = "Email", unique=true, nullable=false)
	private String email;
	@Column(name = "RoleAdmin")
	private boolean roleAdmin;
	
	
	public ApplicationUser() {}
	
	public ApplicationUser(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
}
