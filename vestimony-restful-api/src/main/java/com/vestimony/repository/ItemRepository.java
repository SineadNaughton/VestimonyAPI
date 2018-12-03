package com.vestimony.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.vestimony.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	public List<Item> findByNameLikeIgnoreCase(String itemName);
	
	public Optional<Item> findByUrl(String url);

	//public Iterable<Item> findAllOrderByPriceDesc();

	//public Iterable<Item> findAllOrderByPriceAsc();

	//public Iterable<Item> findAllOrderByRatingDesc();

	//public Iterable<Item> findAllOrderByCreateDateTimeDesc();

	public List<Item> findByBrandLikeIgnoreCaseAndCategoryLikeIgnoreCase(String brand, String category);
	
	public List<Item> findByBrandLikeIgnoreCaseAndCategoryLikeIgnoreCaseAndNameLikeIgnoreCase(String brand, String colour, String name);
	public List<Item> findByBrandLikeIgnoreCaseAndColourLikeIgnoreCase(List<String> brands, List<String> colour);
	
	public List<Item> findByCreatedDateTimeBetweenOrderByRatingDesc(LocalDateTime start, LocalDateTime end);
	public List<Item> findByCreatedDateTimeBetweenOrderByCreatedDateTimeDesc(LocalDateTime start, LocalDateTime end);


	//filter sort
	
}
