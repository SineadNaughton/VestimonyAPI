package com.vestimony.service;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import com.jayway.jsonpath.JsonPath;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.vestimony.model.Item;
import com.vestimony.repository.ItemRepository;

@Service
public class WebCrawlerService {
	@Autowired
	private ItemRepository itemRespository;

	public void crawlTopShopNewIn() throws UnirestException {
		final HttpResponse<String> response = Unirest.get(
				"http://eu.topshop.com/webapp/wcs/stores/servlet/CatalogNavigationAjaxSearchResultCmd?storeId=13058&catalogId=34058&langId=-1&dimSelected=%2Fen%2Ftseu%2Fcategory%2Fnew-in-this-week-2169943%2Fnew-in-fashion-6367516%2FN-a0qZdgf%3FNrpp%3D20%26Ns%3Dproduct.freshnessRank%257C0%26siteId%3D%252F13058%26sort_field%3DNewness%26categoryId%3D339026")
				.asString();
		
		
		

		String bodyString = response.getBody();
		Integer recordsLength = JsonPath.read(bodyString, "$.results.contents[0].records.length()");

		// foreach record in page
		for (int i = 0; i < recordsLength; i++) {
			LinkedHashMap record = JsonPath.read(bodyString, "$.results.contents[0].records[" + i + "]");
			String resultName = JsonPath.read(bodyString, "$.results.contents[0].records[" + i + "].name");
			Double resultPrice = JsonPath.read(bodyString, "$.results.contents[0].records[" + i + "].nowPrice");
			String resultColour = JsonPath.read(bodyString, "$.results.contents[0].records[" + i + "].colour");
			String resultImageUrl = JsonPath.read(bodyString,"$.results.contents[0].records[" + i + "].productImageUrl");
			String resultProductUrl = JsonPath.read(bodyString, "$.results.contents[0].records[" + i + "].productUrl");
			System.out.println(resultName);
			System.out.println(resultPrice);
			System.out.println(resultColour);
			System.out.println(resultImageUrl);
			System.out.println(resultProductUrl);
			
			Item item = new Item(resultName, resultColour, resultPrice, "Topshop", resultProductUrl, resultImageUrl);
			itemRespository.save(item);
		}
	}
	
	public void crawlAsosNewIn() throws UnirestException {
		
			final HttpResponse<String> response = Unirest.get(
					"https://api.asos.com/product/search/v1/categories/2623?channel=desktop-web&country=IE&currency=GBP&keyStoreDataversion=ckp5egq-11&lang=en&limit=72&offset=0&rowlength=4&sort=freshness&store=1")
					.asString();
			
			
			

			String bodyString = response.getBody();
			Integer recordsLength = JsonPath.read(bodyString, "$.products.length()");

			// foreach record in page
			for (int i = 0; i < recordsLength; i++) {
				String resultName = JsonPath.read(bodyString, "$.products[" + i + "].name");
				Double resultPrice = JsonPath.read(bodyString, "$.products[" + i + "].price");
				/*String resultColour = JsonPath.read(bodyString, "$.results.contents[0].records[" + i + "].colour");
				String resultImageUrl = JsonPath.read(bodyString,
						"$.results.contents[0].records[" + i + "].productImageUrl");
				String resultProductUrl = JsonPath.read(bodyString, "$.results.contents[0].records[" + i + "].productUrl");
				System.out.println(resultName);
				System.out.println(resultPrice);
				System.out.println(resultColour);
				System.out.println(resultImageUrl);
				System.out.println(resultProductUrl);*/
				
				System.out.println(resultName);
				System.out.println(resultPrice);
				
				/*Item item = new Item(resultName, resultColour, resultPrice, "Topshop", resultProductUrl, resultImageUrl);
				itemRespository.save(item);*/
			}
	}
}
