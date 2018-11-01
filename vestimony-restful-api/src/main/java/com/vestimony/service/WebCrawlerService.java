package com.vestimony.service;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.vestimony.model.Item;
import com.vestimony.repository.ItemRepository;

@Service
public class WebCrawlerService {
	@Autowired
	private ItemRepository itemRespository;
	

Configuration conf = Configuration.builder().options(Option.DEFAULT_PATH_LEAF_TO_NULL).build();

	public void crawlTopShopNewIn() throws UnirestException {
		final HttpResponse<String> response = Unirest.get(
				"http://eu.topshop.com/webapp/wcs/stores/servlet/CatalogNavigationAjaxSearchResultCmd?storeId=13058&catalogId=34058&langId=-1&dimSelected=%2Fen%2Ftseu%2Fcategory%2Fnew-in-this-week-2169943%2Fnew-in-fashion-6367516%2FN-a0qZdgf%3FNrpp%3D20%26Ns%3Dproduct.freshnessRank%257C0%26siteId%3D%252F13058%26sort_field%3DNewness%26categoryId%3D339026")
				.asString();
		
		
		

		String bodyString = response.getBody();
		Integer recordsLength = JsonPath.read(bodyString, "$.results.contents[0].records.length()");

		// foreach record in page
		for (int i = 0; i < recordsLength; i++) {
			LinkedHashMap record = JsonPath.using(conf).parse(bodyString).read("$.results.contents[0].records[" + i + "]");
			String resultName = JsonPath.using(conf).parse(bodyString).read("$.results.contents[0].records[" + i + "].name");
			Double resultPrice = JsonPath.using(conf).parse(bodyString).read("$.results.contents[0].records[" + i + "].nowPrice");
			String resultColour = JsonPath.using(conf).parse(bodyString).read("$.results.contents[0].records[" + i + "].colour");
			String resultImageUrl = JsonPath.using(conf).parse(bodyString).read("$.results.contents[0].records[" + i + "].productImageUrl");
			String resultProductUrl = JsonPath.using(conf).parse(bodyString).read("$.results.contents[0].records[" + i + "].productUrl");
			System.out.println(resultName);
			System.out.println(resultPrice);
			System.out.println(resultColour);
			System.out.println(resultImageUrl);
			System.out.println(resultProductUrl);
			
			Item item = new Item(resultName, resultColour, resultPrice, "Topshop", resultProductUrl, resultImageUrl);
			item.decideItemCategory(item);
			
			
			Optional<Item> itemExists = itemRespository.findByUrl(resultProductUrl);
			if(!itemExists.isPresent()) {
				
				itemRespository.save(item);
			}
			
		}
	}
	
	public void crawlAsosNewIn() throws UnirestException {
		
		Unirest.setDefaultHeader("Cookie",
				"__gads=ID=1b8e67318a127c3c:T=1539105850:S=ALNI_MbHz2oYVmrNNe5oI_ISjc0yAuculw; bt_recUser=0; _gcl_au=1.1.746380592.1539170920; _ga=GA1.2.2076113610.1539170921; gig_hasGmid=ver2; cto_lwid=845195fd-c74d-4b8b-8943-4f213fa87c47; fita.sid.asos=3JXc-N-UXV0LutB_S6Pa38KExe8ZqmyO; _abck=DE064E26B75421A25EA1CFF85D5EC78317CBF92F2F1200003AE4BC5B71007D63~0~s2Saz5dTOI5EYrVmlMuEJ6dpv7YMuFriXoisNo5O8WM=~-1~-1; bt_pubref=1023; asosConnectTransId=1021bef14e80dd00c072b001f88f47; bt_stdstatus=NOTSTUDENT; glt_3_Gl66L3LpFTiwZ8jWQ9x_4MLyUUHPRmPtRni0hzJ9RH5WA2Ro6tUv47yNXtKn3HQ8=LT3_R4a7YfjhtkZPv5Td3-OXBebkXBhBHGNxvESduV4hfwg%7CUUID%3Db485bd3cd6634b6cab39a331e6ff0b6a; signal.awc=7602_1539767262_dd0e10dabe4b6daaf0576b46a03fa111; bt_gclid=CjwKCAjwu5veBRBBEiwAFTqDwcVKrhJjEjhBJR57FMuIbfx-2uOD4SirFbqBIPIso-qAqYfZ6srpfRoCdvgQAvD_BwE; bt_affid=21569; _gcl_aw=GCL.1539770356.CjwKCAjwu5veBRBBEiwAFTqDwcVKrhJjEjhBJR57FMuIbfx-2uOD4SirFbqBIPIso-qAqYfZ6srpfRoCdvgQAvD_BwE; _gcl_dc=GCL.1539770356.CjwKCAjwu5veBRBBEiwAFTqDwcVKrhJjEjhBJR57FMuIbfx-2uOD4SirFbqBIPIso-qAqYfZ6srpfRoCdvgQAvD_BwE; _gac_UA-1005942-121=1.1539770356.CjwKCAjwu5veBRBBEiwAFTqDwcVKrhJjEjhBJR57FMuIbfx-2uOD4SirFbqBIPIso-qAqYfZ6srpfRoCdvgQAvD_BwE; _s_pl=1000; geocountry=IE; check=true; AMCVS_C0137F6A52DEAFCC0A490D4C%40AdobeOrg=1; asos-b-sdv629=ckp5egq-11; s_cc=true; asos-gdpr22=true; bm_sz=976F2DCBAD7BC4171C0285BC578806B1~QAAQ7t7dWAduYqRmAQAAnZLzsKtKSblGtXDYZZ8fVsdnKMIjivBcFYyvCobwGfIlA5NTEuCdEpeX8MhyT7cj7w3R2SxEinerXMg+HSE2IaoqomVGML6Mn8TFS3fY2vScI6IruZEqXVF3n9kMgBjFOETPNNhFC/zq/nznpXVkkYpAkovZGvjiBEP8bR27; _gid=GA1.2.1022030953.1540567047; mbox=PC#84066c67c782496ab2a25f289ed428c4.26_5#1603098766|session#1673d1e702cf4dabac37eb8a14b4bd23#1540569913; s_pers=%20s_vnum%3D1541030400605%2526vn%253D18%7C1541030400605%3B%20gpv_p6%3D%2520%7C1540569848726%3B%20s_invisit%3Dtrue%7C1540569852995%3B%20s_nr%3D1540568052997-Repeat%7C1572104052997%3B%20gpv_e47%3Dno%2520value%7C1540569852998%3B%20gpv_p10%3Ddesktop%2520com%257Ccategory%2520page%257C2623%7C1540569853000%3B; AMCV_C0137F6A52DEAFCC0A490D4C%40AdobeOrg=1099438348%7CMCIDTS%7C17831%7CMCMID%7C62367702093677956880679768982934619023%7CMCAAMLH-1540985623%7C6%7CMCAAMB-1541172853%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1540574245s%7CNONE%7CvVersion%7C2.1.0%7CMCAID%7CNONE%7CMCCIDH%7C974044489; s_sq=%5B%5BB%5D%5D; ak_bmsc=4584B96B257B393333C45EADB04C530058DDDEECA06F0000715AD35B1F24F232~plVIzyhw1RUEYB2aVdNqHSa63bspBPpxCKZacVqHqcjIeE1ii2D7jdx0eo3eeBQRotUKkfu/maO2d6juQhTn6YkWmzqGxKmMnY73/4hnqvkqzzri3WOJOWE33/ncdhls/kqHCY+8BT7rAfWkugFpU2aRUgOHafvX5/V+JeFbKOMSgOhD2P4pvS1eUda1QDh8VsSDHKMTMsnzsi2JAliNrWs5/WSRSSrkvgfq66FeHxZdM=; X-Mapping-bmnehckk=8333DF7A442A60D205D0F79F161E6048; bm_sv=CD7B4630231B679CE0E9E85F7055EDC1~/+C1NcB+JvN7ARgKd3HtIwJlu7Yw/A+ftMN5tBg+W/rvzrx7n9m61T4gtOUmIL2mGqkR9Z39f5JjFsbfwnCqrs4W2tSOA5ZS4ZhynTF+XbOlY93toygctE5GOAlTsi81zFAUkEC0L0Zkap1AMTNW3Q==");
		final HttpResponse<String> response = Unirest.get(
				"https://api.asos.com/product/search/v1/categories/2623?channel=desktop-web&country=IE&currency=EUR&keyStoreDataversion=ckp5egq-11&lang=en&limit=72&offset=0&rowlength=4&sort=freshness&store=1")
				.asString();

		String bodyString = response.getBody();
		System.out.println(bodyString);
		Integer recordsLength = JsonPath.read(bodyString, "$.products.length()");

		// foreach record in page
		for (int i = 0; i < recordsLength; i++) {
			String resultName = JsonPath.using(conf).parse(bodyString).read("$.products[" + i + "].name");
			Double resultPrice = JsonPath.using(conf).parse(bodyString).read("$.products[" + i + "].price.current.value");

			String resultColour = JsonPath.using(conf).parse(bodyString).read("$.products[" + i + "].colour");
			String resultImageUrl = JsonPath.using(conf).parse(bodyString).read(
					"$.products[" + i + "].baseImageUrl");
			String resultProductUrl = JsonPath.using(conf).parse(bodyString).read("$.products[" + i + "].url");
			System.out.println(resultName);
			System.out.println(resultPrice);
			System.out.println(resultColour);
			System.out.println(resultImageUrl);
			System.out.println(resultProductUrl);

			System.out.println(resultName);
			System.out.println(resultPrice);
				
				Item item = new Item(resultName, resultColour, resultPrice, "Asos", resultProductUrl, resultImageUrl);
				Optional<Item> itemExists = itemRespository.findByUrl(resultProductUrl);
				if(!itemExists.isPresent()) {
					
					itemRespository.save(item);
				}
			}
	}
}
