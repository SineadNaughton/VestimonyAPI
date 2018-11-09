package com.vestimony.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vestimony.model.Vestimonial;
import com.vestimony.service.VestimonialService;

@RestController
@RequestMapping(value = "/vestimony/vestimonials", consumes = "application/json", produces = "application/json")
public class VestimonialController {
	
	@Autowired
	private VestimonialService vestimonialService;
	
	//create
	@PostMapping(value="/{postId}/{itemId}")
	public ResponseEntity<Vestimonial> createVestimonial(@PathVariable("postId") long postId, @PathVariable("itemId") long itemId, @RequestBody Vestimonial vestimonial ) {
		vestimonialService.createVestimonial(vestimonial, itemId, postId);
		return new ResponseEntity<Vestimonial>(vestimonial, HttpStatus.OK);
	}
	
	//link existing
	@PutMapping("/{vestimonialId}/link/{postId}")
	public ResponseEntity<Vestimonial> linkVestimonialToPost(@PathVariable ("vestimonialId") long vestimonialId, @PathVariable ("postId") long postId){
		Vestimonial vestimonial = vestimonialService.linkVestimonialToPost(postId, vestimonialId);
		return new ResponseEntity<Vestimonial>(vestimonial, HttpStatus.OK);
	}
	
	//get all for user
	@GetMapping("/{userId}")
	public ResponseEntity<List<Vestimonial>> getAllVestimonialsForUser(){
		List<Vestimonial> vestimonials = vestimonialService.getAllVestimonialForUser();
		return new ResponseEntity<List<Vestimonial>>(vestimonials, HttpStatus.OK);
	}
	
	//find vest by user and item
	@GetMapping("unique/{itemId}")
	public Optional<Vestimonial> getVest(@PathVariable ("itemId") long itemId){
		return vestimonialService.findByApplicationUserAndItem(itemId);
			
		
	}
	
	

	

	
	

	


}
