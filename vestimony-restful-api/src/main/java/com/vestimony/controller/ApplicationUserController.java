package com.vestimony.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vestimony.model.ApplicationUser;
import com.vestimony.service.ApplicationUserService;



@RestController
@RequestMapping("/vestimony")
public class ApplicationUserController {


	
	@Autowired
	private ApplicationUserService applicationUserService;
	
	@RequestMapping("/users")
	public List<ApplicationUser> getAllUsers(){
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		return applicationUserService.getAllApplicationUsers();
	}
	

	
	@RequestMapping(method=RequestMethod.POST, value="/users")
	public void addUser(@RequestBody ApplicationUser user) {
		applicationUserService.addApplicationUser(user);
	}
}
