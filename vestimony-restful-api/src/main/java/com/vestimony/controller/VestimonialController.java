package com.vestimony.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
//@RequestMapping("/items/{itemId}/vestimonials")
public class VestimonialController {
	
	@Autowired
	private VestimonialService vestimonialService;
	
	//create
	@PostMapping(value="/posts/{postId}/items/{itemId}/vestimonials")
	public void createVestimonial(@PathVariable("postId") long postId, @PathVariable("itemId") long itemId, @RequestBody Vestimonial vestimonial ) {
		vestimonialService.createVestimonial(vestimonial, itemId, postId);
	}
	
	//get
	@GetMapping("/items/{itemId}/vestimonials")
	public List<Vestimonial> getAllVestimonials(@PathVariable("itemId") long itemId){
		return vestimonialService.getAllVestimonials(itemId);
	}
	
	@GetMapping("/items/{itemId}/vestimonials/{vestimonialId")
	public Optional<Vestimonial> getVestimonial(@PathVariable ("vestimonialId") long vestimonialId) {
		return vestimonialService.getVestimonial(vestimonialId);
	}
	
	//get for user
	@GetMapping("/users/{userId}/vestimonials")
	public List<Vestimonial> getAllVestimonialsForUser(){
		return vestimonialService.getAllVestimonialForUser();
	}
	
	@GetMapping("/posts/{postId}/vestimonials")
	public List<Vestimonial> getAllVestimonialsForUserToLinkToPost(){
		return vestimonialService.getAllVestimonialForUser();
	}
	
	@PutMapping("/posts/{postId}/vestimonials/{vestimonialId}")
	public void linkVestimonialToPost(@PathVariable ("vestimonialId") long vestimonialId, @PathVariable ("postId") long postId){
		vestimonialService.linkVestimonialToPost(postId, vestimonialId);
	}

}
