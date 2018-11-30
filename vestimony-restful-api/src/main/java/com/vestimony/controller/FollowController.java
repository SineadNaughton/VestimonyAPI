package com.vestimony.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Post;
import com.vestimony.service.ApplicationUserService;
import com.vestimony.service.FollowService;
import com.vestimony.service.PostService;

@RestController
@RequestMapping(value = "/vestimony/follow", consumes = "application/json", produces = "application/json")
public class FollowController {
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private ApplicationUserService applicationUserService;
	
	@Autowired
	private PostService postService;
	
	
	
	//FOLLOW USER
	@GetMapping("/{userId}")
	public ResponseEntity<String> followUser(@PathVariable long userId) {
		ApplicationUser user = applicationUserService.getCurrentUser();
		ApplicationUser userToFollow = applicationUserService.getApplicationUser(userId);
		String resp = followService.followProfile(user, userToFollow);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}
	
	//UNFOLLOW
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> unfollowUser(@PathVariable long userId)
	{ApplicationUser user = applicationUserService.getCurrentUser();
	ApplicationUser userToUnfollow = applicationUserService.getApplicationUser(userId);
		String resp = followService.unfollowProfile(user, userToUnfollow);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}
	
	//GET POSTS FOR FOLLOWING
	@GetMapping
	public ResponseEntity<List<Post>> getFollowedPosts(){
		ApplicationUser user = applicationUserService.getCurrentUser();
		List<Post> followedPosts = postService.getFollowingPosts(user);
		return new ResponseEntity<List<Post>>(followedPosts, HttpStatus.OK);
		
	}
	
	//CHECK IS FOLLOWING
	@GetMapping(value = "/isfollowing/{userId}")
	public ResponseEntity<Boolean> isUserFollowing(@PathVariable long userId) {
		ApplicationUser currentUser = applicationUserService.getCurrentUser();
		ApplicationUser userToFollow = applicationUserService.getApplicationUser(userId);
		boolean isFollowing = followService.isUserFollowing(currentUser, userToFollow);
		return new ResponseEntity<Boolean>(isFollowing, HttpStatus.OK);
	}
	 

}
