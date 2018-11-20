package com.vestimony.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Item;
import com.vestimony.model.Post;
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

	// GET PROFILE IMAGE
	@GetMapping(value = "/image/{userId}", consumes = MediaType.ALL_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getImage(@PathVariable long userId) {
		byte[] pic = applicationUserService.getApplicationUser(userId).getPic();
		return pic;
	}

	// GET CURRENT USER
	@GetMapping("/currentuser")
	public ResponseEntity<ApplicationUser> getCuurentUser() {
		ApplicationUser applicationUser = applicationUserService.getCurrentnUser();
		return new ResponseEntity<ApplicationUser>(applicationUser, HttpStatus.OK);

	}

	// GET ALL USERS
	@GetMapping
	public ResponseEntity<List<ApplicationUser>> getAllUsers() {
		List<ApplicationUser> applicationUsers = applicationUserService.getAllApplicationUsers();
		return new ResponseEntity<List<ApplicationUser>>(applicationUsers, HttpStatus.OK);
	}

	// GET ONE PROFILE TO VIEW
	@GetMapping(value = "/profiles/{userId}", produces = "application/json")
	public ResponseEntity<UserProfile> getUserProfile(@PathVariable long userId) {
		ApplicationUser applicationUser = applicationUserService.getApplicationUser(userId);
		UserProfile userProfile = userProfileService.getUserProfile(applicationUser);
		return new ResponseEntity<UserProfile>(userProfile, HttpStatus.OK);
	}

	// GET ALL PROFILES OR ONE BY NAME
	@GetMapping("/profiles")
	public ResponseEntity<List<ApplicationUser>> getAllUserProfiles(
			@RequestParam(value = "username", defaultValue = "") String username) {
		List<ApplicationUser> applicationUsers = new ArrayList<>();
		if (username == "") {
			applicationUsers = applicationUserService.getAllApplicationUsers();

		} else {
			applicationUsers = applicationUserService.findApplicationUserByUsername(username);

		}
		//List<UserProfile> userProfiles = userProfileService.getAllUserProfiles(applicationUsers);
		return new ResponseEntity<List<ApplicationUser>>(applicationUsers, HttpStatus.OK);

	}

	// CREATE A PROFILE
	@PostMapping(value = "/signup")
	@CrossOrigin
	public ResponseEntity<String> addUser(@RequestBody ApplicationUser applicationUser) {
		System.out.println(applicationUser);
		String resp = applicationUserService.addApplicationUser(applicationUser);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// ADD PROFILE IMAGE
	@PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public ResponseEntity<String> handlerFileUpload( @RequestParam("file") MultipartFile file)
			throws IOException {
		applicationUserService.addProfileImage(file);
		String resp= "Sucess " + file.getOriginalFilename();
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// SAVE ITEM
	@GetMapping(value = "/saveitem/{itemId}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> saveItem(@PathVariable long itemId) {
		String resp = applicationUserService.saveItem(itemId);
		// add to users liked posts

		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// UNSAVE ITEM
	@GetMapping(value = "/unsaveitem/{itemId}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> unsaveItem(@PathVariable long itemId) {
		String resp = applicationUserService.unsaveItem(itemId);
		// add to users liked posts

		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// CHECK IF USER SAVED ITEM
	@GetMapping(value = "/issaved/{itemId}")
	public ResponseEntity<Boolean> isItemSaved(@PathVariable long itemId) {
		boolean isLiked = applicationUserService.isItemSaved(itemId);
		// add to users liked posts

		return new ResponseEntity<Boolean>(isLiked, HttpStatus.OK);
	}

	// GAT USER'S SAVED ITEMS
	@GetMapping(value = "/saveditems")
	public ResponseEntity<List<Item>> getSavedItems() {
		List<Item> items = applicationUserService.getSavedItems();
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}

	// GET USER'S LIKED POSTS
	@GetMapping(value = "/likedposts")
	public ResponseEntity<List<Post>> getLikedPosts() {
		List<Post> posts = applicationUserService.getLikedPosts();
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

	// GET POSTS CREATED BY USER
	@GetMapping(value = "{userId}/posts")
	public ResponseEntity<List<Post>> getPostsForProfile(@PathVariable long userId) {
		List<Post> posts = applicationUserService.getPostsForPorifle(userId);
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

}
