package com.vestimony.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.vestimony.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

	Image findByImageName(String filename);

}
