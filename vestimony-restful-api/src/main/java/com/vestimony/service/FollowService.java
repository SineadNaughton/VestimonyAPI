package com.vestimony.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vestimony.model.ApplicationUser;
import com.vestimony.repository.ApplicationUserRespository;

@Service
public class FollowService {

	@Autowired
	private ApplicationUserRespository applicationUserRepository;


	// FOLLOW PROFILE
	public String followProfile(ApplicationUser currentUser, ApplicationUser userToFollow) {
		// get list following
		Set<ApplicationUser> following = currentUser.getFollowing();
		// check not already following
		if (!isFollowing(userToFollow, following)) {
			following.add(userToFollow);
			currentUser.setFollowing(following);
			applicationUserRepository.save(currentUser);

			// INCREASE FOLLOWERS OF USER TO FOLLOW
			long numFollowers = userToFollow.getNumFollowers();
			numFollowers++;
			userToFollow.setNumFollowers(numFollowers);
			applicationUserRepository.save(userToFollow);
		}
		return "following user";
	}

	// UNFOLLOW PROFILE
	public String unfollowProfile(ApplicationUser user, ApplicationUser userToUnfollow) {
		// get list following
		Set<ApplicationUser> following = user.getFollowing();
		if (isFollowing(userToUnfollow, following)) {
			following.remove(userToUnfollow);
			user.setFollowing(following);
			applicationUserRepository.save(user);

			// set number of followers
			long numFollowers = userToUnfollow.getNumFollowers();
			numFollowers--;
			userToUnfollow.setNumFollowers(numFollowers);
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


	//CHECK IF A USER IS FOLLOWING ANOTHER
	public boolean isUserFollowing(ApplicationUser currentUser, ApplicationUser userToFollow) {
		Set<ApplicationUser> usersFollowed = currentUser.getFollowing();
		if (usersFollowed.contains(userToFollow)) {
			return true;
		}
		return false;
	}

}
