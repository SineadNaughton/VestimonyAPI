package com.vestimony.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.vestimony.service.WebCrawlerService;

@RestController
public class ItemController {

	@Autowired
	private WebCrawlerService webCrawlerService;
	
	@GetMapping("/vestimony/crawl")
	public void webCrawler() throws UnirestException {
		webCrawlerService.crawlTopShopNewIn();
	}
	
}
