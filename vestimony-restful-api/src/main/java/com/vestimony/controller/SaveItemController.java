package com.vestimony.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Item;
import com.vestimony.service.ApplicationUserService;
import com.vestimony.service.ItemService;
import com.vestimony.service.SaveItemService;

@RestController
@RequestMapping(value = "/vestimony/saveditems", consumes = "application/json", produces = "application/json")
public class SaveItemController {

	@Autowired
	private SaveItemService saveItemService;
	
	@Autowired
	private ApplicationUserService applicationUserService;
	
	@Autowired
	private ItemService itemService;
	
	// SAVE ITEM
		@PostMapping(value = "/{itemId}", produces = MediaType.TEXT_PLAIN_VALUE)
		public ResponseEntity<String> saveItem(@PathVariable long itemId) {
			ApplicationUser user = applicationUserService.getCurrentUser();
			Item item = itemService.findById(itemId);
			String resp = saveItemService.saveItem(user, item);
			// add to users liked posts
			return new ResponseEntity<String>(resp, HttpStatus.OK);
		}
		
		// UNSAVE ITEM
		@DeleteMapping(value = "/{itemId}", produces = MediaType.TEXT_PLAIN_VALUE)
		@CrossOrigin
		public ResponseEntity<String> unsaveItem(@PathVariable long itemId) {
			ApplicationUser user = applicationUserService.getCurrentUser();
			Item item = itemService.findById(itemId);
			String resp = saveItemService.unsaveItem(user, item);
			// add to users liked posts
			return new ResponseEntity<String>(resp, HttpStatus.OK);
		}
		
		
		// CHECK IF USER SAVED ITEM
		@GetMapping(value = "/issaved/{itemId}")
		public ResponseEntity<Boolean> isItemSaved(@PathVariable long itemId) {
			ApplicationUser user = applicationUserService.getCurrentUser();
			Item item = itemService.findById(itemId);
			boolean isLiked = saveItemService.isItemSaved(user, item);
			return new ResponseEntity<Boolean>(isLiked, HttpStatus.OK);
		}

		
		// GET USER'S SAVED ITEMS
		@GetMapping
		public ResponseEntity<List<Item>> getSavedItems() {
			ApplicationUser user = applicationUserService.getCurrentUser();
			List<Item> items = saveItemService.getSavedItems(user);
			return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		}
	
	
}
