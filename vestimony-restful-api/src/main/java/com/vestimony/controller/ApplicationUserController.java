package com.vestimony.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.UserProfile;
import com.vestimony.service.ApplicationUserService;
import com.vestimony.service.PostService;
import com.vestimony.service.UserProfileService;

@RestController
@CrossOrigin
@RequestMapping(value = "/vestimony/users", consumes = "application/json", produces = "application/json")
public class ApplicationUserController {

	@Autowired
	private ApplicationUserService applicationUserService;

	@Autowired
	private UserProfileService userProfileService;
	
	
	// GET ONE PROFILE TO VIEW
	@GetMapping(value = "/profiles/{userId}", produces = "application/json")
	public ResponseEntity<UserProfile> getUserProfile(@PathVariable long userId) {
		ApplicationUser applicationUser = applicationUserService.getApplicationUser(userId);
		UserProfile userProfile = userProfileService.getUserProfile(applicationUser);
		return new ResponseEntity<UserProfile>(userProfile, HttpStatus.OK);
	}

	// GET PROFILE IMAGE
	@GetMapping(value = "/image/{userId}", consumes = MediaType.ALL_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getImage(@PathVariable long userId) {
		byte[] pic = applicationUserService.getApplicationUser(userId).getPic();
		return pic;
	}


	// GET ALL PROFILES OR ONE BY NAME
	@GetMapping("/profiles/search/{username}")
	public ResponseEntity<List<UserProfile>> searchUserProfiles(@PathVariable(value = "username") String username) {
		List<ApplicationUser> applicationUsers = new ArrayList<>();
		if (username == "") {
			applicationUsers = applicationUserService.getAllApplicationUsers();
		} else {
			applicationUsers = applicationUserService.findApplicationUserByUsername(username);
		}
		List<UserProfile> userProfiles = userProfileService.getAllUserProfiles(applicationUsers);
		return new ResponseEntity<List<UserProfile>>(userProfiles, HttpStatus.OK);

	}

	// CREATE A PROFILE
	@PostMapping
	@CrossOrigin
	public ResponseEntity<String> addUser(@RequestBody ApplicationUser applicationUser) {
		
		String resp = applicationUserService.addApplicationUser(applicationUser);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// ADD PROFILE IMAGE
	@PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public ResponseEntity<String> addProfileImage( @RequestParam("file") MultipartFile file)
			throws IOException {
		applicationUserService.addProfileImage(file);
		String resp= "Sucess " + file.getOriginalFilename();
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}
	
	//Edit Account details
	@PutMapping(value = "/edit")
	@CrossOrigin
	public ResponseEntity<String> editUser(@RequestBody ApplicationUser applicationUser) {
		String resp = applicationUserService.editApplicationUser(applicationUser);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}
	
	//Get current user
	@GetMapping(value = "/currentuser", produces = "application/json")
	public ResponseEntity<ApplicationUser> getCurrentUsere() {
		ApplicationUser applicationUser = applicationUserService.getCurrentUser();
		return new ResponseEntity<ApplicationUser>(applicationUser, HttpStatus.OK);
	}

	


}
