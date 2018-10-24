package com.vestimony.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.vestimony.model.ApplicationUser;
import com.vestimony.repository.ApplicationUserRespository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	@Autowired
	private ApplicationUserRespository applicationUserRespository;
	
	//load user from DB - hardcoaded for now
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApplicationUser applicationUser = loadApplicationUserByUsername(username);
		UserDetails user = User.withUsername(applicationUser.getUsername())
                .password(encoder.encode(applicationUser.getPassword()))
                .roles("USER").build();
		
		 return user;
	}

	//gets details from db
	//public ApplicationUser loadApplicationUserByUsername(String username) {
	//	return new ApplicationUser ("batman", "pass5", "sinead@gmail.com");
	//}
	
	public ApplicationUser loadApplicationUserByUsername(String username) {
		return applicationUserRespository.findByUsername(username);
	}
}

