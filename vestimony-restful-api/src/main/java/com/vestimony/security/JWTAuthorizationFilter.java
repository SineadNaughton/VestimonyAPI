package com.vestimony.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.vestimony.service.CustomUserDetailsService;

import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private final CustomUserDetailsService customUserDetailsService;

	//this data will come from security config
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService ) {
		super(authenticationManager);
		this.customUserDetailsService = customUserDetailsService;
	}
	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	throws IOException, ServletException {
		//super.doFilterInternal(request, response, chain); this blocks any requats not authorized
		String header = request.getHeader(SecurityConstants.HEADER_STRING);
		
		//if it's not correct format it won't pass
		if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken usernamePasswordAuth = getAuthenticationToken(request);
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuth);
		filterChain.doFilter(request, response);
	}
	//checking token
	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		
		if(token==null) {return null;}
		
		String username = Jwts.parser().setSigningKey(SecurityConstants.SECRET)
				.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
				.getBody()
				.getSubject();
		
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
		// ApplicationUser applicationUser = customUserDetailsService.loadByApplicationuserName(username);
		return username!=null? new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) : null;
	}
}
