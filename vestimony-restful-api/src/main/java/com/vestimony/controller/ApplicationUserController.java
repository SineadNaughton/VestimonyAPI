package com.vestimony.controller;

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
import org.springframework.web.bind.annotation.RestController;

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

	// get current user
	@GetMapping("/currentuser")
	public ResponseEntity<ApplicationUser> getCuurentUser() {
		ApplicationUser applicationUser = applicationUserService.getCurrentnUser();
		return new ResponseEntity<ApplicationUser>(applicationUser, HttpStatus.OK);

	}

	// get all users
	@GetMapping
	public ResponseEntity<List<ApplicationUser>> getAllUsers() {
		List<ApplicationUser> applicationUsers = applicationUserService.getAllApplicationUsers();
		return new ResponseEntity<List<ApplicationUser>>(applicationUsers, HttpStatus.OK);
	}

	// get one profile to view
	@GetMapping("/profiles/{userId}")
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
	public ResponseEntity<List<UserProfile>> getAllUserProfiles(
			@RequestParam(value = "username", defaultValue = "") String username) {
		List<ApplicationUser> applicationUsers = new ArrayList<>();
		if (username == "") {
			applicationUsers = applicationUserService.getAllApplicationUsers();

		} else {
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

	// save item
	// like post
	@GetMapping(value = "/saveitem/{itemId}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> saveItem(@PathVariable long itemId) {
		String resp = applicationUserService.saveItem(itemId);
		// add to users liked posts

		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// unsave item
	@GetMapping(value = "/unsaveitem/{itemId}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> unsaveItem(@PathVariable long itemId) {
		String resp = applicationUserService.unsaveItem(itemId);
		// add to users liked posts

		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// is item saved
	@GetMapping(value = "/issaved/{itemId}")
	public ResponseEntity<Boolean> isItemSaved(@PathVariable long itemId) {
		boolean isLiked = applicationUserService.isItemSaved(itemId);
		// add to users liked posts

		return new ResponseEntity<Boolean>(isLiked, HttpStatus.OK);
	}
	
	//get saved items
	@GetMapping(value="/saveditems")
	public ResponseEntity<List<Item>> getSavedItems() {
		List<Item> items = applicationUserService.getSavedItems();
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}
	
	
	//get liked posts
	
	@GetMapping(value="/likedposts")
	public ResponseEntity<List<Post>> getLikedPosts() {
		List<Post> posts = applicationUserService.getLikedPosts();
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}
	
	//get posts created by user
	@GetMapping(value="/profile/{userId}/posts")
	public ResponseEntity<List<Post>> getPostsForProfile(@PathVariable long userId){
		List<Post> posts = applicationUserService.getPostsForPorifle(userId);
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

}
