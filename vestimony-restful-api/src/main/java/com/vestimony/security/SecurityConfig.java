package com.vestimony.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.vestimony.service.CustomUserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//this allows preauthorize annotation to work
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}


	//define config rules
	//anyone allowed on sign up page
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/sign_up").permitAll()
		.antMatchers("/*/vestimony/**").hasRole("USER")
		.antMatchers("/*/floor2/**").hasRole("ADMIN")
		.and()
		.addFilter(new JWTAuthenticationFilter(authenticationManager()))//add filters authManager coming from config adaptor
		.addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailsService));
	}

}
