package com.vestimony.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Post;
import com.vestimony.service.FollowService;

@RestController
@RequestMapping(value = "/vestimony/follow", consumes = "application/json", produces = "application/json")
public class FollowController {
	
	@Autowired
	private FollowService followService;
	
	
	//FOLLOW USER
	@GetMapping("/{userId}")
	public ResponseEntity<String> followUser(@PathVariable long userId) {
		String resp = followService.followProfile(userId);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}
	
	//UNFOLLOW
	@GetMapping("/unfollow/{userId}")
	public ResponseEntity<String> unfollowUser(@PathVariable long userId) {
		String resp = followService.unfollowProfile(userId);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}
	
	//GET POSTS FOR FOLLOWING
	@GetMapping
	public ResponseEntity<List<Post>> getFollowedPosts(){
		List<Post> followedPosts = followService.getFollowingPosts();
		return new ResponseEntity<List<Post>>(followedPosts, HttpStatus.OK);
		
	}
	
	//CHECK IS FOLLOWING
	@GetMapping(value = "/isfollowing/{userId}")
	public ResponseEntity<Boolean> isUserFollowing(@PathVariable long userId) {
		boolean isFollowing = followService.isUserFollowing(userId);
		return new ResponseEntity<Boolean>(isFollowing, HttpStatus.OK);
	}
	 

}
