package com.vestimony.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.vestimony.model.Item;
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
	
	@SuppressWarnings("deprecation")
	public List<Item> findByBrandLikeAndColourLike(String brand, String colour){
		List<Item> items = new ArrayList<>();
		itemRepository.findByBrandLikeIgnoreCaseAndColourLikeIgnoreCase("%"+brand+"%", "%"+colour+"%", new PageRequest(0, 10, Direction.DESC, "price") ).forEach(items::add);
		return items;
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
	


}
