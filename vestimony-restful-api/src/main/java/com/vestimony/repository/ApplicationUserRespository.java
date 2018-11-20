package com.vestimony.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.vestimony.model.ApplicationUser;
@Repository
@CrossOrigin
public interface ApplicationUserRespository extends JpaRepository<ApplicationUser, Long> {

	public List<ApplicationUser> findByUsernameLikeIgnoreCase(String username);
	public Optional<ApplicationUser> findByUsernameIgnoreCase(String username);
	public ApplicationUser findByUsername(String username);
	public Optional<ApplicationUser> findByEmail(String email);


}
