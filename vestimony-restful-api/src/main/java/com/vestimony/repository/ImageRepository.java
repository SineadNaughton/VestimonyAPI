package com.vestimony.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.vestimony.model.Image;
import com.vestimony.model.Post;

public interface ImageRepository extends JpaRepository<Image, Long> {

	Image findByImageName(String filename);
	
	Image findFirstByPost(Post Post);

}
