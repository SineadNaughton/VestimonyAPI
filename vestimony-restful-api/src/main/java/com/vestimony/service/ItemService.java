package com.vestimony.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Item;
import com.vestimony.repository.ApplicationUserRespository;
import com.vestimony.repository.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	//search by name
	public List<Item> search(String itemName){
		return itemRepository.findByNameLikeIgnoreCase("%"+itemName+"%");
	}
	
	//filter
	public List<Item> findByBrandCategoryName(String brand, String category, String name){
		List<Item> items = new ArrayList<>();
		itemRepository.findByBrandLikeIgnoreCaseAndCategoryLikeIgnoreCaseAndNameLikeIgnoreCase("%"+brand+"%", "%"+category+"%", "%"+name+"%").forEach(items::add);
		return items;
	}
	
	//filter
	public List<Item> findByBrandLikeAndColourLike(String brand, String category){
		List<Item> items = new ArrayList<>();
		itemRepository.findByBrandLikeIgnoreCaseAndCategoryLikeIgnoreCase("%"+brand+"%", "%"+category+"%").forEach(items::add);
		return items;
	}
	
	//filter
	public List<Item> findByBrandLikeAndColour(String brand, String colour){
		List<Item> items = new ArrayList<>();
		itemRepository.findByBrandLikeIgnoreCaseAndCategoryLikeIgnoreCase("%"+brand+"%", "%"+colour+"%").forEach(items::add);
		return items;
	}
	
	/*//filter
	public List<Item> findByBrandLikeAndColourLike(List<String> brands, List<String> colours){
		List<Item> items = new ArrayList<>();
		int i = brands.size();
		itemRepository.findByBrandLikeIgnoreCaseAndColourLikeIgnoreCase("%"+brand+"%", "%"+colour+"%").forEach(items::add);
		return items;
	}*/
	
	//find by id
	public Item findById(long itemId) {
		return itemRepository.findById(itemId).get();
	}
	

	
	//sort========================================================
	/*
	//price - ASc/desc
	public List<Item> findAllOrderByPriceDesc(){
		List<Item> items = new ArrayList<>();
		itemRepository.findAllOrderByPriceDesc().forEach(items::add);
		return items;
	}
	
	public List<Item> findAllOrderByPriceAsc(){
		List<Item> items = new ArrayList<>();
		itemRepository.findAllOrderByPriceAsc().forEach(items::add);
		return items;
	}
	//rating
	/*public List<Item> findAllOrderByRatingDesc(){
		List<Item> items = new ArrayList<>();
		itemRepository.findAllOrderByRatingDesc().forEach(items::add);
		return items;
	}
	
	//new
	public List<Item> findAllOrderByCreateDateTimeDesc(){
		List<Item> items = new ArrayList<>();
		itemRepository.findAllOrderByCreateDateTimeDesc().forEach(items::add);
		return items;
	}*/
	
	//display top rated
	public List<Item> getAll(){
		List<Item> items = new ArrayList<>();
		itemRepository.findAll().forEach(items::add);
		return items;
	}




	public List<Item> getTopRated() {
		List<Item> items = new ArrayList<>();		
		LocalDateTime localDateTimeFrom = LocalDateTime.now().minusDays(60);
		LocalDateTime localDateTimeTo = LocalDateTime.now();
		
		itemRepository.findByCreatedDateTimeBetweenOrderByRatingDesc(localDateTimeFrom, localDateTimeTo).forEach(items::add);
		return items;
	}

	public List<Item> getNewest() {
		List<Item> items = new ArrayList<>();		
		LocalDateTime localDateTimeFrom = LocalDateTime.now().minusDays(60);
		LocalDateTime localDateTimeTo = LocalDateTime.now();
		
		itemRepository.findByCreatedDateTimeBetween(localDateTimeFrom, localDateTimeTo).forEach(items::add);
		return items;
	}
	
	


}
