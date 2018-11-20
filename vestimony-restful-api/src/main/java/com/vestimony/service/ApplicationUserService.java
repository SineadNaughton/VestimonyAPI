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
import com.vestimony.model.Image;
import com.vestimony.model.Item;
import com.vestimony.model.Post;
import com.vestimony.repository.ApplicationUserRespository;
import com.vestimony.repository.ItemRepository;

@Service
public class ApplicationUserService {

	@Autowired
	private ApplicationUserRespository applicationUserRepository;
	
	@Autowired
	private ItemRepository itemRepository;

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
		return "Signed up sucessfully";
	}

	// UPDATE APP USER
	public void updateApplicationUser(ApplicationUser applicationUser) {
		applicationUserRepository.save(applicationUser);
	}

	// DELETE APP USER
	public void removeApplicationUser(long id) {
		applicationUserRepository.deleteById(id);
	}

	//GET THE CURRENT APP USER
	public ApplicationUser getCurrentnUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		return user;
	}
	
	//SAVE AN ITEM CURRENT USER LIKES
	public String saveItem(long itemId) {
		ApplicationUser user = this.getCurrentnUser();
		Item item = itemRepository.findById(itemId).get();
		
		Set<Item> savedItems = user.getSavedItems();
		savedItems.add(item);
		user.setSavedItems(savedItems);
		applicationUserRepository.save(user);
		
		//increase saved
		long numSaved = item.getNumSaved();
		numSaved++;
		item.setNumSaved(numSaved);
		itemRepository.save(item);
		
		return "Item Saved";
		
		
	}
	
	//UNSAVE ITEM 
	public String unsaveItem(long itemId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		Item item = itemRepository.findById(itemId).get();
		
		Set<Item> savedItems = user.getSavedItems();
		savedItems.remove(item);
		user.setSavedItems(savedItems);
		applicationUserRepository.save(user);
		
		//decrease saved
		long numSaved = item.getNumSaved();
		numSaved--;
		item.setNumSaved(numSaved);
		itemRepository.save(item);
		
		return "Saved item removed";
		
	}



	//CHECK IF CURRENT USER HAS SAVED AN ITEM
	public boolean isItemSaved(long itemId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		Item item = itemRepository.findById(itemId).get();
		Set<Item> savedItems = user.getSavedItems();
		if(savedItems.contains(item)) {
			return true;
		}
		return false;
	}

	//GET A LIST OF THE POSTS CURRENT USER HAS LIKED
	public List<Post> getLikedPosts() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		Set<Post> likedPosts = user.getLikedPost();
		 List<Post> posts = new ArrayList<Post>(likedPosts);
		 return posts;
		
	}

	//GET LIST OF SAVED ITEMS
	public List<Item> getSavedItems() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		Set<Item> savedItems = user.getSavedItems();
		 List<Item> items = new ArrayList<Item>(savedItems);
		 return items;
	}
	
	//GET LIST OF POSTS FOR A USERS PROFILE
	public List<Post> getPostsForPorifle(long userId) {
		ApplicationUser user = applicationUserRepository.findById(userId).get();
		List<Post> posts = new ArrayList<>(user.getPosts());
		return posts;
		
	}
	
	//ADD IMAGE TO USER PROFILE
	public void addProfileImage(MultipartFile file) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		if (!file.isEmpty()) {
			byte[] pic = ByteStreams.toByteArray(file.getInputStream());
			user.setPic(pic);
			applicationUserRepository.save(user);
		}
		
	}
	


}
