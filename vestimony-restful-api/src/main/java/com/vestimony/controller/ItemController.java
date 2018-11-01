package com.vestimony.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/vestimony/items")
public class ItemController {

	@Autowired
	private WebCrawlerService webCrawlerService;
	@Autowired
	private ItemService itemService;
	
	@GetMapping("/crawl")
	public void webCrawler() throws UnirestException {
		webCrawlerService.crawlTopShopNewIn();
		webCrawlerService.crawlAsosNewIn();
	}
	
	@GetMapping(value = "/search")
	public List<Item> search(@RequestParam("itemName") String itemName){
		return itemService.search(itemName);
	}
	
	@GetMapping
	public List<Item> getAll(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getPrincipal());
		return itemService.getAll();
		
	}
	
	//filter
	@GetMapping(value="/filter")
	public List<Item> filter(@RequestParam(value = "brand", defaultValue = "") String brand, 
								@RequestParam(value = "colour", defaultValue = "") String colour){
		return itemService.findByBrandLikeAndColourLike(brand, colour);
	}
	

	
	//sort
	
	//
}
