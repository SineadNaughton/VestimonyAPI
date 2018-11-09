package com.vestimony.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
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

	public List<Item> findByBrandLikeIgnoreCaseAndColourLikeIgnoreCase(String brand, String colour);


	//filter sort
	
}
