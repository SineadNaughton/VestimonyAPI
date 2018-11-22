package com.vestimony.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Item;
import com.vestimony.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	
	Iterable<Post> findByApplicationUserOrderByCreatedDateTimeDesc(ApplicationUser followed);
	
	public List<Post> findByCreatedDateTimeBetweenOrderByNumLikesDesc(LocalDateTime start, LocalDateTime end);

	Iterable<Post> findByApplicationUser(ApplicationUser user);
}
