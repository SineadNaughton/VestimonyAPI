package com.vestimony.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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

	/*
	 * OLD SEARCH METHOD
	 * 
	 * @GetMapping(value = "/search") public List<Item>
	 * search(@RequestParam("itemName") String itemName){ return
	 * itemService.search(itemName); }
	 */

	// get all
	@GetMapping
	public ResponseEntity<List<Item>> getAll() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getPrincipal());
		List<Item> items = itemService.getAll();
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}

	// filter
	@GetMapping(value = "/filter")
	public ResponseEntity<List<Item>> filter(@RequestParam(defaultValue="") String brand,
			@RequestParam(defaultValue="") String colour) {
		List<Item> items = itemService.findByBrandLikeAndColourLike(brand, colour);
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}

	//
}
