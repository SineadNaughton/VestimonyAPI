package com.vestimony.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vestimony.model.Item;
import com.vestimony.model.Vestimonial;
import com.vestimony.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	
	//get newly added items
	public List<Item> getNewest() {
		List<Item> items = new ArrayList<>();
		LocalDateTime localDateTimeFrom = LocalDateTime.now().minusDays(60);
		LocalDateTime localDateTimeTo = LocalDateTime.now();

		itemRepository.findByCreatedDateTimeBetweenOrderByCreatedDateTimeDesc(localDateTimeFrom, localDateTimeTo).forEach(items::add);
		return items;
	}
	
	// get one item
	public Item findById(long itemId) {
		return itemRepository.findById(itemId).get();
	}


	// filter
	public List<Item> findByBrandCategoryName(String brand, String category, String name) {
		List<Item> items = new ArrayList<>();
		itemRepository.findByBrandLikeIgnoreCaseAndCategoryLikeIgnoreCaseAndNameLikeIgnoreCase("%" + brand + "%",
				"%" + category + "%", "%" + name + "%").forEach(items::add);
		return items;
	}

	

	// display top rated
	public List<Item> getAll() {
		List<Item> items = new ArrayList<>();
		itemRepository.findAll().forEach(items::add);
		return items;
	}

	public List<Item> getTopRated() {
		List<Item> items = new ArrayList<>();
		LocalDateTime localDateTimeFrom = LocalDateTime.now().minusDays(60);
		LocalDateTime localDateTimeTo = LocalDateTime.now();

		itemRepository.findByCreatedDateTimeBetweenOrderByRatingDesc(localDateTimeFrom, localDateTimeTo)
				.forEach(items::add);
		return items;
	}

	public void updateItemStats(Vestimonial vestimonial, Item item) {
		// update item rating
		int vestimonialRating = vestimonial.getRating();
		float overallRating = item.getRating();
		long numberReviews = item.getNumReviews();
		float total = (int) numberReviews * overallRating;
		float newRating = (total + vestimonialRating) / ((int) numberReviews + 1);
		item.setRating(newRating);

		// update item size adjustment
		int adjustment = vestimonial.getSizeBought() - vestimonial.getUsualSize();
		if (adjustment != 0 && !(item.getCategory().equals("shoe"))) {
			adjustment = adjustment / 2;
		}
		float adjustmentCurrentlyRecommended = item.getSizeAdjustment() * (int) numberReviews;
		float newRecommendedAdjustment = (adjustmentCurrentlyRecommended + adjustment) / ((int) numberReviews + 1);
		item.setSizeAdjustment(newRecommendedAdjustment);

		// update num reviews
		numberReviews++;
		item.setNumReviews(numberReviews);

		// save item
		itemRepository.save(item);

	}
	
	//link to buy
	public void followLinkToBuy(long itemId) {
		Item item = this.findById(itemId);
		long numFollowedLink = item.getLinkToBuy() +1;
		item.setLinkToBuy(numFollowedLink);
		itemRepository.save(item);
	}

}
