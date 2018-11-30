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

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Item;
import com.vestimony.model.Post;
import com.vestimony.model.Vestimonial;
import com.vestimony.service.ApplicationUserService;
import com.vestimony.service.ItemService;
import com.vestimony.service.PostService;
import com.vestimony.service.VestimonialService;

@RestController
@RequestMapping(value = "/vestimony/vestimonials", consumes = "application/json", produces = "application/json")
public class VestimonialController {

	@Autowired
	private VestimonialService vestimonialService;

	@Autowired
	private ApplicationUserService applicationUserService;

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private PostService postService;

	// get one
	@GetMapping(value = "/{vestimonialId}")
	public ResponseEntity<Vestimonial> geOneVestimonial(@PathVariable("vestimonialId") long vestimonialId) {
		Vestimonial vestimonial = vestimonialService.findById(vestimonialId);
		return new ResponseEntity<Vestimonial>(vestimonial, HttpStatus.OK);
	}

	// get all for user
	@GetMapping("/currentuser")
	public ResponseEntity<List<Vestimonial>> getAllVestimonialsForUser() {
		ApplicationUser user = applicationUserService.getCurrentUser();
		List<Vestimonial> vestimonials = vestimonialService.getAllVestimonialForUser(user);
		return new ResponseEntity<List<Vestimonial>>(vestimonials, HttpStatus.OK);
	}

	// get all for item
	@GetMapping("/items/{itemId}")
	public ResponseEntity<List<Vestimonial>> getAllVestimonialsForItem(@PathVariable("itemId") long itemId) {
		Item item = itemService.findById(itemId);
		List<Vestimonial> vestimonials = vestimonialService.getAllVestimonialForItem(item);
		return new ResponseEntity<List<Vestimonial>>(vestimonials, HttpStatus.OK);
	}

	// create
	@PostMapping(value = "/{postId}/{itemId}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<Vestimonial> createVestimonial(@PathVariable("postId") long postId,
			@PathVariable("itemId") long itemId, @RequestBody Vestimonial vestimonial) {
		ApplicationUser user = applicationUserService.getCurrentUser();
		Item item = itemService.findById(itemId);
		Post post = postService.getOnePost(postId).get();
		Vestimonial resp = vestimonialService.createVestimonial(vestimonial, user, item, post);
		return new ResponseEntity<Vestimonial>(resp, HttpStatus.OK);
	}

	// link existing
	@PostMapping("/{vestimonialId}/link/{postId}")
	public ResponseEntity<String> linkVestimonialToPost(@PathVariable("vestimonialId") long vestimonialId,
			@PathVariable("postId") long postId) {
		Post post = postService.getOnePost(postId).get();
		String resp = vestimonialService.linkVestimonialToPost(post, vestimonialId);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}



}
