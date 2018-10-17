package com.vestimony.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vestimony.model.ApplicationUser;
@Repository
public interface ApplicationUserRespository extends JpaRepository<ApplicationUser, Long> {

	public ApplicationUser findByUsername(String username);

}
