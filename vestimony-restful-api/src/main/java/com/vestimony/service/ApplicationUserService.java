package com.vestimony.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.ByteStreams;
import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Post;
import com.vestimony.repository.ApplicationUserRespository;

@Service
public class ApplicationUserService {

	@Autowired
	private ApplicationUserRespository applicationUserRepository;
	
	//GET CURRENTY USER
	public ApplicationUser getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		return user;
	}

	// GET ALL APPLCIATION USERS
	public List<ApplicationUser> getAllApplicationUsers() {
		List<ApplicationUser> applicationUsers = new ArrayList<>();
		applicationUserRepository.findAll().forEach(applicationUsers::add);
		return applicationUsers;
	}
	
	//GET APPLICAITON USER BY NAME
	public List<ApplicationUser> findApplicationUserByUsername(String username) {
		List<ApplicationUser> applicationUsers = new ArrayList<>();
		applicationUserRepository.findByUsernameLikeIgnoreCase("%"+username+"%").forEach(applicationUsers::add);
		return applicationUsers;
	}

	// GET APPLICAITON USER BY ID
	public ApplicationUser getApplicationUser(long id) {
		return applicationUserRepository.findById(id).get();
	}

	// CREATE APP USER
	public String addApplicationUser(ApplicationUser applicationUser) {
		String username = applicationUser.getUsername();
		Optional<ApplicationUser> user = applicationUserRepository.findByUsernameIgnoreCase(username);
		
		if (user.isPresent()){
			return "Username already exists, chose another";
		}
		String email = applicationUser.getEmail();
		user = applicationUserRepository.findByEmail(email);
		if (user.isPresent()){
			return "Email already exists, please sign in";
		}
		
		applicationUserRepository.save(applicationUser);
		return "Signed up successfully";
	}

	// UPDATE APP USER
	public void updateApplicationUser(ApplicationUser applicationUser) {
		applicationUserRepository.save(applicationUser);
	}

	// DELETE APP USER
	public void removeApplicationUser(long id) {
		applicationUserRepository.deleteById(id);
	}
	
	
	//ADD IMAGE TO USER PROFILE
		public void addProfileImage(MultipartFile file) throws IOException {
			ApplicationUser user = this.getCurrentUser();
			if (!file.isEmpty()) {
				byte[] pic = ByteStreams.toByteArray(file.getInputStream());
				user.setPic(pic);
				applicationUserRepository.save(user);
			}
			
		}

	
	//GET A LIST OF THE POSTS CURRENT USER HAS LIKED
	public List<Post> getLikedPosts() {
		ApplicationUser user = this.getCurrentUser();
		Set<Post> likedPosts = user.getLikedPost();
		 List<Post> posts = new ArrayList<Post>(likedPosts);
		 return posts;
		
	}


	

	
	
	//EDIT ACCOUNT
	public String editApplicationUser(ApplicationUser applicationUser) {
		ApplicationUser user = this.getCurrentUser();
	
		user.setBio(applicationUser.getBio());
		user.setHeightFeet(applicationUser.getHeightFeet());
		user.setHeightInches(applicationUser.getHeightInches());
		user.setSizeBottom(applicationUser.getSizeBottom());
		user.setSizeTop(applicationUser.getSizeTop());
		applicationUserRepository.save(user);
		return "OK";
	}
	


}
