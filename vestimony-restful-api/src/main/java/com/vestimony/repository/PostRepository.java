package com.vestimony.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	
	List<Post> findFirst2ByApplicationUserOrderByCreateDateTimeDesc(ApplicationUser followed);
}
