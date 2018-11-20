package com.vestimony.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Item;
import com.vestimony.model.Post;
import com.vestimony.model.Vestimonial;
import com.vestimony.repository.ApplicationUserRespository;
import com.vestimony.repository.ItemRepository;
import com.vestimony.repository.PostRepository;
import com.vestimony.repository.VestimonialRepository;

@Service
public class VestimonialService {

	@Autowired
	private VestimonialRepository vestimonialRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ApplicationUserRespository applicationUserRepository;

	@Autowired
	private PostRepository postRepository;

	// create new --add post to this
	public String createVestimonial(Vestimonial vestimonial, long postId, long itemId) {
		// get user and item
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		Item item = itemRepository.findById(itemId).get();
		
		vestimonial.setApplicationUser(user);
		vestimonial.setItem(item);
		
		// check if exists
		if (!vestimonialRepository.findByApplicationUserAndItem(user, item).isPresent()) {

			// associate with post
			vestimonialRepository.save(vestimonial);
			
			Post post = postRepository.findById(postId).get();
			Set<Vestimonial> vestimonials = post.getVestimonials();
			vestimonials.add(vestimonial);
			post.setVestimonials(vestimonials);
			postRepository.save(post);
			
			
			// update item rating
			int vestimonialRating = vestimonial.getRating();
			int overallRating = item.getRating();
			long numberReviews = item.getNumReviews();
			int total = (int) numberReviews * overallRating;
			int newRating = (total + vestimonialRating) / ((int) numberReviews+1);
			item.setRating(newRating);
			
			int adjustment = vestimonial.getSizeBought() - vestimonial.getUsualSize();
			int adjustmentCurrentlyRecommended = item.getSizeAdjustment() * (int)numberReviews;
			int newRecommendedAdjustment = (adjustmentCurrentlyRecommended + adjustment) /((int) numberReviews+1);
			item.setSizeAdjustment(newRecommendedAdjustment);
			item.setNumReviews(numberReviews++);
			
			
			
			
			//save item
			itemRepository.save(item);
			return "vestimonial created";
		}
		else {
			Vestimonial vestimonalExists = vestimonialRepository.findByApplicationUserAndItem(user, item).get();
			return "vestimonials exists fro this item";  //link vestimonial
		}
		
	}


	// link
	public String linkVestimonialToPost(long postId, long vestimonialId) {
		Post post = postRepository.findById(postId).get();
		Vestimonial vestimonial = vestimonialRepository.findById(vestimonialId).get();
		Set<Vestimonial> vestimonials = post.getVestimonials();
		vestimonials.add(vestimonial);
		post.setVestimonials(vestimonials);
		postRepository.save(post);
		return "Sucessfully linked";
	}

	

	// get all for user
	public List<Vestimonial> getAllVestimonialForUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		List<Vestimonial> vestimonials = new ArrayList<>();
		user.getVestimonials().forEach(vestimonials::add);
		return vestimonials;
	}
	
	public Optional<Vestimonial> findByApplicationUserAndItem(long itemId){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		
		Item item = itemRepository.findById(itemId).get();
		return vestimonialRepository.findByApplicationUserAndItem(user, item);
	}


	public Vestimonial findById(long vestimonialId) {
		Vestimonial vestimonial = vestimonialRepository.findById(vestimonialId).get();
		return vestimonial;
	}




	
}
