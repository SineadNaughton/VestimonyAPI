package com.vestimony.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.UserProfile;
import com.vestimony.service.ApplicationUserService;
import com.vestimony.service.UserProfileService;



@RestController

public class ApplicationUserController {


	
	@Autowired
	private ApplicationUserService applicationUserService;
	@Autowired
	private UserProfileService userProfileService;
	
	//get all
	@RequestMapping("/vestimony/users")
	public List<ApplicationUser> getAllUsers(){
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		return applicationUserService.getAllApplicationUsers();
	}
	

	//create
	@RequestMapping(method=RequestMethod.POST, value="/vestimony/signup")
	public void addUser(@RequestBody ApplicationUser user) {
		applicationUserService.addApplicationUser(user);
	}
	
	//get one profile
	@GetMapping("/vestimony/users/{userId}/profiles")
	public UserProfile getUserProfile(@PathVariable long userId){
		Optional<ApplicationUser> appUser = applicationUserService.getApplicationUser(userId);
		if (appUser.isPresent()) return userProfileService.getUserProfile(appUser.get());
		return null; 
	}
	

	
}
