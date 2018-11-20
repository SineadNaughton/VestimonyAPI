package com.vestimony.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.vestimony.service.WebCrawlerService;
@RestController
public class WebCrawlerController {
	
	@Autowired
	private WebCrawlerService webCrawlerService;
	
	@RequestMapping(value="/vestimony/webcrawler", consumes = "application/json", produces = "application/json")
	public void webCrawler() throws UnirestException, IOException {
		webCrawlerService.crawlTopShopNewIn();
		webCrawlerService.crawlAsosNewIn();
	}

}
