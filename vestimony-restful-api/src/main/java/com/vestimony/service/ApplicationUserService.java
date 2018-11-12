package com.vestimony.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vestimony.model.ApplicationUser;
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

	// get all
	public List<ApplicationUser> getAllApplicationUsers() {
		List<ApplicationUser> applicationUsers = new ArrayList<>();
		applicationUserRepository.findAll().forEach(applicationUsers::add);
		return applicationUsers;
	}
	
	//get by name
	public List<ApplicationUser> findApplicationUserByUsername(String username) {
		List<ApplicationUser> applicationUsers = new ArrayList<>();
		applicationUserRepository.findByUsernameLikeIgnoreCase("%"+username+"%").forEach(applicationUsers::add);
		return applicationUsers;
	}


	// get one by id
	public Optional<ApplicationUser> getApplicationUser(long id) {
		return applicationUserRepository.findById(id);
	}

	// add one
	public ApplicationUser addApplicationUser(ApplicationUser applicationUser) {
		applicationUserRepository.save(applicationUser);
		return applicationUser;
	}

	// update
	public void updateApplicationUser(ApplicationUser applicationUser) {
		applicationUserRepository.save(applicationUser);
	}

	// delete
	public void removeApplicationUser(long id) {
		applicationUserRepository.deleteById(id);
	}

	public ApplicationUser getCurrentnUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		return user;
	}
	
	//save item
	public String saveItem(long itemId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
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
	
	public String unsaveItem(long itemId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		Item item = itemRepository.findById(itemId).get();
		
		Set<Item> savedItems = user.getSavedItems();
		savedItems.remove(item);
		user.setSavedItems(savedItems);
		applicationUserRepository.save(user);
		
		//increase saved
		long numSaved = item.getNumSaved();
		numSaved--;
		item.setNumSaved(numSaved);
		itemRepository.save(item);
		
		return "Saved item removed";
		
	}




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

	public List<Post> getLikedPosts() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		Set<Post> likedPosts = user.getLikedPost();
		 List<Post> posts = new ArrayList<Post>(likedPosts);
		 return posts;
		
	}

}
