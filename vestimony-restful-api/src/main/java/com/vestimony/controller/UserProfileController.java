package com.vestimony.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.UserProfile;
import com.vestimony.service.UserProfileService;

@RestController
@RequestMapping("/vestimony")
public class UserProfileController {
	/*
	@Autowired
	private UserProfileService userProfileService;
	
	@RequestMapping("/users/{userId}/profiles")
	public Optional<UserProfile> getProfile(@PathVariable long userId){
		return userProfileService.getUserProfile(userId);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/users/{userId}/profiles")
	public void addUserProfile(@RequestBody UserProfile userProfile, @PathVariable long userId) {
		userProfile.setApplicationUser(new ApplicationUser(userId,"","", ""));
		userProfileService.addUserProfile(userProfile);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/users/{userId}/profiles/{userProfileId}")
	public void updateUserProfile(@RequestBody UserProfile userProfile, @PathVariable long userId, @PathVariable long userProfileId ) {
		userProfile.setApplicationUser(new ApplicationUser(userId,"","", ""));
		userProfile.setUserProfileId(userProfileId);
		userProfileService.updateUserProfile(userProfile);
	}
*/
}
