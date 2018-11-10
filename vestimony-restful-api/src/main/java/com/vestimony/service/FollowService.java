package com.vestimony.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Post;
import com.vestimony.repository.ApplicationUserRespository;
import com.vestimony.repository.PostRepository;

@Service
public class FollowService {

	@Autowired
	private ApplicationUserRespository applicationUserRepository;
	
	@Autowired
	private PostRepository postRepository;
	

	public ApplicationUser followProfile(long userId) {
		// set user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		// get list following
		Set<ApplicationUser> following = user.getFollowing();
		// get user to add
		ApplicationUser userToFollow = applicationUserRepository.findById(userId).get();
		// check not already following
		if (!isFollowing(userToFollow, following)) {
			following.add(userToFollow);
			user.setFollowing(following);
			applicationUserRepository.save(user);
		}
		return user;
	}

	public String unfollowProfile(long userId) {
		// set user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		// get list following
		Set<ApplicationUser> following = user.getFollowing();
		// get user to add
		ApplicationUser userToUnfollow = applicationUserRepository.findById(userId).get();
		if (isFollowing(userToUnfollow, following)) {
			following.remove(userToUnfollow);
			user.setFollowing(following);
			applicationUserRepository.save(user);
			return "User unfollowed";
		}
		return "not following user";

	}

	// if already following
	boolean isFollowing(ApplicationUser userToFollow, Set<ApplicationUser> following) {
		if (following.contains(userToFollow)) {
			return true;
		}
		return false;
	}

	// get posts from following
	// find all user id in following
	// loop through each one and get the last ten posts from each account.
	public List<Post> getFollowingPosts() {
		// set user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		// get list following
		Set<ApplicationUser> following = user.getFollowing();
		
		//list of post
		List<Post> followedPosts =new ArrayList<>();
		for(ApplicationUser followed: following) {
			postRepository.findFirst2ByApplicationUserOrderByCreateDateTimeDesc(followed).forEach(followedPosts::add);
		}
		
		return followedPosts;
	}
	
	

}
