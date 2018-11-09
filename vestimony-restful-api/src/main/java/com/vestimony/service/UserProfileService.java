package com.vestimony.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.UserProfile;


@Service
public class UserProfileService {

	public UserProfile getUserProfile(ApplicationUser appUser) {
		UserProfile profile = new UserProfile(appUser);
		return profile;
	}

	public List<UserProfile> getAllUserProfiles(List<ApplicationUser> applicationUsers) {
		List<UserProfile> userProfiles = new ArrayList<>();
		for (ApplicationUser appUser : applicationUsers) {
			UserProfile profile = new UserProfile(appUser);
			userProfiles.add(profile);
		}
		return userProfiles;

	}
	
	//get profile by name that match
	

	/*
	 * //public Optional<UserProfile> getUserProfile(long id){ UserProfile profile =
	 * userProfileRespository.findByUserProfileId(id); //Optional<Soundcard> sc =
	 * Optional.ofNullable(soundcard); Optional<UserProfile> userProfile =
	 * Optional.ofNullable(profile);
	 * 
	 * return userProfile;
	 * 
	 * }
	 * 
	 * 
	 * 
	 * public void addUserProfile(UserProfile userProfile) { UserProfile profile =
	 * userProfileRespository.findByUserProfileId(userProfile.getApplicationUser().
	 * getUserId()); userProfileRespository.save(userProfile); }
	 * 
	 * public void updateUserProfile(UserProfile userProfile) {
	 * userProfileRespository.save(userProfile); }
	 */
}
