package com.vestimony.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Item;
import com.vestimony.model.Vestimonial;


@Repository
public interface VestimonialRepository extends JpaRepository<Vestimonial, Long> {
	
	List<Vestimonial> findByApplicationUser(long uerId);
	
	List<Vestimonial> findByItem(Item item);
	
	Optional<Vestimonial> findByApplicationUserAndItem(ApplicationUser user, Item item);

}
