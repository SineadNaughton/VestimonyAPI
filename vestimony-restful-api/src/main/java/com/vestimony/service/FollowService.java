package com.vestimony.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Item;
import com.vestimony.model.Post;
import com.vestimony.repository.ApplicationUserRespository;
import com.vestimony.repository.PostRepository;

@Service
public class FollowService {

	@Autowired
	private ApplicationUserRespository applicationUserRepository;

	@Autowired
	private PostRepository postRepository;

	// FOLLOW PROFILE
	public String followProfile(long userId) {
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

			// INCREASE FOLLOWERS OF USER TO FOLLOW
			long numFollowers = userToFollow.getNumFollowers();
			userToFollow.setNumFollowers(numFollowers++);
			applicationUserRepository.save(userToFollow);
		}
		return "following user";
	}

	// UNFOLLOW PROFILE
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

			// set number of followers
			long numFollowers = userToUnfollow.getNumFollowers();
			userToUnfollow.setNumFollowers(numFollowers++);
			applicationUserRepository.save(userToUnfollow);

			return "User unfollowed";

		}
		return "not following user";

	}

	// CHECK if already following
	boolean isFollowing(ApplicationUser userToFollow, Set<ApplicationUser> following) {
		if (following.contains(userToFollow)) {
			return true;
		}
		return false;
	}

	// GET POSTS FROM TEH USERS YOU FOLLOW
	public List<Post> getFollowingPosts() {
		// set user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		// get list following
		Set<ApplicationUser> following = user.getFollowing();

		// list of post
		List<Post> followedPosts = new ArrayList<>();
		for (ApplicationUser followed : following) {
			postRepository.findByApplicationUserOrderByCreatedDateTimeDesc(followed).forEach(followedPosts::add);
		}

		return followedPosts;
	}

	public boolean isUserFollowing(long userId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser currentUser = applicationUserRepository.findByUsername(auth.getName());
		ApplicationUser followUser = applicationUserRepository.findById(userId).get();
		Set<ApplicationUser> usersFollowed = currentUser.getFollowing();
		if (usersFollowed.contains(followUser)) {
			return true;
		}
		return false;
	}

}
