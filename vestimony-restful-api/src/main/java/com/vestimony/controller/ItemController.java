package com.vestimony.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vestimony.model.Item;
import com.vestimony.service.ApplicationUserService;
import com.vestimony.service.ItemService;

@RestController

@RequestMapping(value = "/vestimony/items", consumes = "application/json", produces = "application/json")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@Autowired
	private ApplicationUserService applicationUserService;

	// get one
	@GetMapping("/{itemId}")
	public ResponseEntity<Item> getOne(@PathVariable long itemId) {
		Item item = itemService.findById(itemId);
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	// newest
	@GetMapping(value = "/new")
	public ResponseEntity<List<Item>> getNewest() {
		List<Item> items = itemService.getNewest();
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}

	// image getter
	@GetMapping(value = "/image/{itemId}", consumes = MediaType.ALL_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getImage(@PathVariable long itemId) {
		return itemService.findById(itemId).getPic();
	}

	// get all
	@GetMapping
	public ResponseEntity<List<Item>> getAll() {
		List<Item> items = itemService.getAll();
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}

	@GetMapping(value = "/filter")
	public ResponseEntity<List<Item>> filter(@RequestParam(defaultValue = "") String brand,
			@RequestParam(defaultValue = "") String category, @RequestParam(defaultValue = "") String name) {
		List<Item> items = itemService.findByBrandCategoryName(brand, category, name);
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}

	// trending
	@GetMapping(value = "/toprated")
	public ResponseEntity<List<Item>> getTopRated() {
		List<Item> items = itemService.getTopRated();
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}
	
	//increase num followed link to buy
	@PostMapping(value = "/linktobuy/{itemId}")
	public ResponseEntity<String> followLinkToBuy(@PathVariable long itemId, @RequestParam(defaultValue = "") String userId) {
		itemService.followLinkToBuy(itemId);
		if (userId!="") {
			long id = Integer.parseInt(userId);
			applicationUserService.followLinkToBuy(id);
		}
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	

}
