package com.vestimony.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.UserProfile;

import com.vestimony.service.ApplicationUserService;
import com.vestimony.service.CustomUserDetailsService;
import com.vestimony.service.UserProfileService;

@RestController
@CrossOrigin
@RequestMapping(value = "/vestimony/users", consumes = "application/json", produces = "application/json")
public class ApplicationUserController {

	@Autowired
	private ApplicationUserService applicationUserService;
	@Autowired
	private UserProfileService userProfileService;

	// get all users
	@GetMapping
	public ResponseEntity<List<ApplicationUser>> getAllUsers() {
		List<ApplicationUser> applicationUsers = applicationUserService.getAllApplicationUsers();
		return new ResponseEntity<List<ApplicationUser>>(applicationUsers, HttpStatus.OK);
	}

	// get one profile to view
	@GetMapping("/{userId}/profiles")
	public ResponseEntity<UserProfile> getUserProfile(@PathVariable long userId) {
		Optional<ApplicationUser> applicationUser = applicationUserService.getApplicationUser(userId);
		if (applicationUser.isPresent()) {
			UserProfile userProfile = userProfileService.getUserProfile(applicationUser.get());
			return new ResponseEntity<UserProfile>(userProfile, HttpStatus.OK);
		}
		return null;
	}

	// get all profiles to view / or by name
	@GetMapping("/profiles")
	public ResponseEntity<List<UserProfile>> getAllUserProfiles(@RequestParam(value ="username", defaultValue="") String username) {
		List<ApplicationUser> applicationUsers = new ArrayList<>();
		if(username=="") {
			applicationUsers = applicationUserService.getAllApplicationUsers();
			
		}
		else {
			applicationUsers = applicationUserService.findApplicationUserByUsername(username);
			
		}
		List<UserProfile> userProfiles = userProfileService.getAllUserProfiles(applicationUsers);
		return new ResponseEntity<List<UserProfile>>(userProfiles, HttpStatus.OK);
		
	}
	
	// create
		@PostMapping(value = "/signup")
		@CrossOrigin
		public ResponseEntity<ApplicationUser> addUser(@RequestBody ApplicationUser applicationUser) {
			System.out.println(applicationUser);
			applicationUserService.addApplicationUser(applicationUser);
			return new ResponseEntity<ApplicationUser>(applicationUser, HttpStatus.OK);
		}

	
	
	

	/*
	 * OLD ALL METHOD //get all profiles
	 * 
	 * @RequestMapping("/vestimony/users") public List<ApplicationUser>
	 * getAllUsers() {
	 * System.out.println(SecurityContextHolder.getContext().getAuthentication());
	 * return applicationUserService.getAllApplicationUsers(); }
	 * 
	 * OLD GET ONE PROFILE METHOD
	 * 
	 * @GetMapping("/{userId}/profiles") public UserProfile
	 * getUserProfile(@PathVariable long userId) { Optional<ApplicationUser> appUser
	 * = applicationUserService.getApplicationUser(userId); if (appUser.isPresent())
	 * return userProfileService.getUserProfile(appUser.get()); return null; }
	 */

	
}
