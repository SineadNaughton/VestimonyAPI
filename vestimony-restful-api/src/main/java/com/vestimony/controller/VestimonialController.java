package com.vestimony.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	
	//get one
	@GetMapping(value="/{vestimonialId}")
	public ResponseEntity<Vestimonial> geOneVestimonial(@PathVariable("vestimonialId") long vestimonialId){
		Vestimonial vestimonial = vestimonialService.findById(vestimonialId);
		return new ResponseEntity<Vestimonial>(vestimonial, HttpStatus.OK);
	}
	
	//create
	@PostMapping(value="/{postId}/{itemId}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> createVestimonial(@PathVariable("postId") long postId, @PathVariable("itemId") long itemId, @RequestBody Vestimonial vestimonial ) {
		String resp = vestimonialService.createVestimonial(vestimonial, postId, itemId);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}
	
	//link existing
	@GetMapping("/{vestimonialId}/link/{postId}")
	public ResponseEntity<String> linkVestimonialToPost(@PathVariable ("vestimonialId") long vestimonialId, @PathVariable ("postId") long postId){
		String resp = vestimonialService.linkVestimonialToPost(postId, vestimonialId);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}
	
	//get all for user
	@GetMapping("/currentuser")
	public ResponseEntity<List<Vestimonial>> getAllVestimonialsForUser(){
		List<Vestimonial> vestimonials = vestimonialService.getAllVestimonialForUser();
		return new ResponseEntity<List<Vestimonial>>(vestimonials, HttpStatus.OK);
	}
	
	//get all for item
	@GetMapping("/items/{itemId}")
	public ResponseEntity<List<Vestimonial>> getAllVestimonialsForItem(@PathVariable ("itemId") long itemId){
		List<Vestimonial> vestimonials = vestimonialService.getAllVestimonialForItem(itemId);
		return new ResponseEntity<List<Vestimonial>>(vestimonials, HttpStatus.OK);
	}
	
	//find vest by user and item
	@GetMapping("unique/{itemId}")
	public Optional<Vestimonial> getVest(@PathVariable ("itemId") long itemId){
		return vestimonialService.findByApplicationUserAndItem(itemId);
			
		
	}
	
	

	

	
	

	


}
