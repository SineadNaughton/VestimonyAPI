package com.vestimony.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vestimony.model.ApplicationUser;
import com.vestimony.repository.ApplicationUserRespository;



@Service
public class ApplicationUserService {

	@Autowired
	private ApplicationUserRespository applicationUserRespository;
	
	//get all
	public List<ApplicationUser> getAllApplicationUsers(){
		List<ApplicationUser> users = new ArrayList<>();
		applicationUserRespository.findAll()
		.forEach(users::add);
		return users;
	}
	
	//get one by id
	public Optional<ApplicationUser> getApplicationUser(long id) {
		return applicationUserRespository.findById(id);
	}
	
	//add one
	public void addApplicationUser(ApplicationUser applicationUser) {
		applicationUserRespository.save(applicationUser);
	}
	
	//update
	public void updateApplicationUser(ApplicationUser applicationUser) {
		applicationUserRespository.save(applicationUser);
	}

	//delete
	public void removeApplicationUser(long id) {
		applicationUserRespository.deleteById(id);
	}
	

}
