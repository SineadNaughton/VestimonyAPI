package com.vestimony.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vestimony.model.ApplicationUser;
import com.vestimony.repository.ApplicationUserRespository;

@Service
public class FollowService {

	@Autowired
	private ApplicationUserRespository applicationUserRepository;

	public ApplicationUser followProfile(long userId) {
		// set user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		
		Set<ApplicationUser> following = user.getFollowing();
		ApplicationUser userToFollow = applicationUserRepository.findById(userId).get();
		following.add(userToFollow);
		user.setFollowing(following); 
		applicationUserRepository.save(user);
		return user;
	}

}
