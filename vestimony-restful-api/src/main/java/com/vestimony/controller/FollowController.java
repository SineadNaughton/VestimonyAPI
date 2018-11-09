package com.vestimony.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vestimony.model.ApplicationUser;
import com.vestimony.service.FollowService;

@RestController
@RequestMapping(value = "/vestimony/follow", consumes = "application/json", produces = "application/json")
public class FollowController {
	
	@Autowired
	private FollowService followService;
	
	
	
	@GetMapping("/{userId}")
	public ApplicationUser followUser(@PathVariable long userId) {
		return followService.followProfile(userId);
	}

}
