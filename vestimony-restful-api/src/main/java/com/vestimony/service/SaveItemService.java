package com.vestimony.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Item;
import com.vestimony.repository.ApplicationUserRespository;
import com.vestimony.repository.ItemRepository;

@Service
public class SaveItemService {

	
	@Autowired
	private ApplicationUserRespository applicationUserRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	//SAVE AN ITEM CURRENT USER LIKES
		public String saveItem(ApplicationUser user, Item item) {
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
		
		//UNSAVE AN ITEM CURRENT USER LIKES
		public String unsaveItem(ApplicationUser user, Item item) {
			Set<Item> savedItems = user.getSavedItems();
			savedItems.remove(item);
			user.setSavedItems(savedItems);
			applicationUserRepository.save(user);
			//decrease saved
			long numSaved = item.getNumSaved();
			numSaved--;
			item.setNumSaved(numSaved);
			itemRepository.save(item);
			return "Item unSaved";
		}
		
		//CHECK IF ITEM IS SAVED
		public boolean isItemSaved(ApplicationUser user, Item item) {
			Set<Item> savedItems = user.getSavedItems();
			if(savedItems.contains(item)) {
				return true;
			}
			return false;
		}
		
		//GET LIST OF SAVED ITEMS
		public List<Item> getSavedItems(ApplicationUser user) {
			Set<Item> savedItems = user.getSavedItems();
			 List<Item> items = new ArrayList<Item>(savedItems);
			 return items;
		}
		
		
}
