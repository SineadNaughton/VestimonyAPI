package com.vestimony.service;

import org.springframework.stereotype.Service;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.UserProfile;

@Service
public class UserProfileService {

	public UserProfile getUserProfile(ApplicationUser appUser) {
		UserProfile profile = new UserProfile(appUser);
		return profile;
	}
	
	/*
	//public Optional<UserProfile> getUserProfile(long id){
		UserProfile profile = userProfileRespository.findByUserProfileId(id);
		//Optional<Soundcard> sc = Optional.ofNullable(soundcard); 
		Optional<UserProfile> userProfile = Optional.ofNullable(profile);
		
		return userProfile;
		
	}
	

	
	public void addUserProfile(UserProfile userProfile) {
		UserProfile profile = userProfileRespository.findByUserProfileId(userProfile.getApplicationUser().getUserId());
		userProfileRespository.save(userProfile);
	}
	
	public void updateUserProfile(UserProfile userProfile) {
		userProfileRespository.save(userProfile);
	}
*/
}
