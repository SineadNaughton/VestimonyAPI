package com.vestimony.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.vestimony.model.Item;
import com.vestimony.service.ItemService;
import com.vestimony.service.WebCrawlerService;

@RestController

@RequestMapping(value = "/vestimony/items", consumes = "application/json", produces = "application/json")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@GetMapping(value = "/search")
	public ResponseEntity<List<Item>> search(@RequestParam("itemName") String itemName) {
		List<Item> items = itemService.search(itemName);
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);

	}
	
	//image getter
	@GetMapping(value = "/image/{itemId}", consumes = MediaType.ALL_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getImage(@PathVariable long itemId) {
		return itemService.findById(itemId).getPic();
	}

	// get all
	@GetMapping
	public ResponseEntity<List<Item>> getAll() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getPrincipal());
		List<Item> items = itemService.getAll();
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}

	// filter
	/*@GetMapping(value = "/filter")
	public ResponseEntity<List<Item>> filter(@RequestParam(defaultValue="") String brand,
			@RequestParam(defaultValue="") String category) {
		List<Item> items = itemService.findByBrandLikeAndColourLike(brand, category);
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}*/
	
	@GetMapping(value = "/filter/colour")
	public ResponseEntity<List<Item>> filterColour(@RequestParam(defaultValue="") String brand,
			@RequestParam(defaultValue="") String colour) {
		List<Item> items = itemService.findByBrandLikeAndColour(brand, colour);
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}
	
	@GetMapping(value = "/filter")
	public ResponseEntity<List<Item>> filterFull(@RequestParam(defaultValue="") String brand,
			@RequestParam(defaultValue="") String category, @RequestParam(defaultValue="") String name) {
		List<Item> items = itemService.findByBrandCategoryName(brand, category, name);
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}
	
	// get one
		@GetMapping("/{itemId}")
		public ResponseEntity<Item> getOne(@PathVariable long itemId) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			System.out.println(auth.getPrincipal());
			Item item = itemService.findById(itemId);
			return new ResponseEntity<Item>(item, HttpStatus.OK);
		}

		// trending
		@GetMapping(value = "/toprated")
		public ResponseEntity<List<Item>> getTopRated() {
			List<Item> items = itemService.getTopRated();
			return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		}
	
		
		// newest
		@GetMapping(value = "/new")
		public ResponseEntity<List<Item>> getNewest() {
			List<Item> items = itemService.getNewest();
			return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		}

}
