package com.vestimony.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vestimony.model.Vestimonial;


@Repository
public interface VestimonialRepository extends JpaRepository<Vestimonial, Long> {
	
	List<Vestimonial> findByApplicationUser(long uerId);
	List<Vestimonial> findByItem(long itemId);
	Optional<Vestimonial> findByApplicationUserAndItem(long userId, long itemId);

}
