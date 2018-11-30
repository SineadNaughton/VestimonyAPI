package com.vestimony.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Item;
import com.vestimony.model.Post;
import com.vestimony.model.Vestimonial;
import com.vestimony.repository.PostRepository;
import com.vestimony.repository.VestimonialRepository;

@Service
public class VestimonialService {

	@Autowired
	private VestimonialRepository vestimonialRepository;

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ItemService itemService;

	// GET ONE VESTIMONIAL
	public Vestimonial findById(long vestimonialId) {
		Vestimonial vestimonial = vestimonialRepository.findById(vestimonialId).get();
		return vestimonial;
	}

	// get all for user
	public List<Vestimonial> getAllVestimonialForUser(ApplicationUser user) {
		List<Vestimonial> vestimonials = new ArrayList<>();
		user.getVestimonials().forEach(vestimonials::add);
		return vestimonials;
	}

	// get all for item
	public List<Vestimonial> getAllVestimonialForItem(Item item) {
		List<Vestimonial> vestimonials = new ArrayList<>();
		vestimonialRepository.findByItem(item).forEach(vestimonials::add);
		return vestimonials;
	}

	// create new --add post to this
	public Vestimonial createVestimonial(Vestimonial vestimonial, ApplicationUser user, Item item, Post post) {
		// set user and item
		vestimonial.setApplicationUser(user);
		vestimonial.setItem(item);
		// check if exists
		if (!vestimonialRepository.findByApplicationUserAndItem(user, item).isPresent()) {
			// associate with post
			vestimonialRepository.save(vestimonial);
			Set<Vestimonial> vestimonials = post.getVestimonials();
			vestimonials.add(vestimonial);
			post.setVestimonials(vestimonials);
			postRepository.save(post);
			//update item
			itemService.updateItemStats(vestimonial, item);
			return vestimonial;
		} else {
			Vestimonial vestimonalExists = vestimonialRepository.findByApplicationUserAndItem(user, item).get();
			return vestimonalExists;
		}

	}

	// link
	public String linkVestimonialToPost(Post post, long vestimonialId) {
		Vestimonial vestimonial = vestimonialRepository.findById(vestimonialId).get();
		Set<Vestimonial> vestimonials = post.getVestimonials();
		if(vestimonials.contains(vestimonial)) {
			return "Already linked";
		}
		vestimonials.add(vestimonial);
		post.setVestimonials(vestimonials);
		postRepository.save(post);
		return "Sucessfully linked";
	}



}
