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
	public void createVestimonial(Vestimonial vestimonial, long itemId, long postId) {
		// user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		vestimonial.setApplicationUser(user);
		// item
		Item item = itemRepository.findById(itemId).get();
		vestimonial.setItem(item);
		// check if exists
		//if (vestimonialRepository.findByApplicationUserAndItem(user.getUserId(), itemId)==null) {

			// post
			Post post = postRepository.findById(postId).get();
			Set<Vestimonial> vestimonials = post.getVestimonials();
			vestimonials.add(vestimonial);
			post.setVestimonials(vestimonials);
			postRepository.save(post);
			vestimonialRepository.save(vestimonial);

			// update item rating

			int vestimonialRating = vestimonial.getRating();
			int overallRating = item.getRating();
			long numberReviews = item.getNumReviews();
			int total = (int) numberReviews * overallRating;
			item.setNumReviews(item.getNumReviews() + 1);
			numberReviews++;
			int newRating = (total + vestimonialRating) / (int) numberReviews;
			item.setRating(newRating);
			itemRepository.save(item);
		//}
	}

	// link
	// link
	public void linkVestimonialToPost(long postId, long vestimonialId) {
		Post post = postRepository.findById(postId).get();
		Vestimonial vestimonial = vestimonialRepository.findById(vestimonialId).get();
		Set<Vestimonial> vestimonials = post.getVestimonials();
		vestimonials.add(vestimonial);
		post.setVestimonials(vestimonials);
		postRepository.save(post);
	}

	// not currently neded
	/*
	 * public void linkAnotherVestimonialToPost(long postId, long vestimonialId) {
	 * Post post = postRepository.findById(postId).get(); Vestimonial vestimonial =
	 * vestimonialRespository.findById(vestimonialId).get(); Set<Vestimonial>
	 * vestimonials = post.getVestimonials(); vestimonials.add(vestimonial);
	 * post.setVestimonials(vestimonials); postRepository.saveAndFlush(post); }
	 */

	// get all
	public List<Vestimonial> getAllVestimonials(long itemId) {

		List<Vestimonial> vestimonials = new ArrayList<>();
		vestimonialRepository.findAll().forEach(vestimonials::add);
		return vestimonials;
	}

	// get one
	public Optional<Vestimonial> getVestimonial(long vestimonialId) {
		return vestimonialRepository.findById(vestimonialId);
	}

	// get all for user
	public List<Vestimonial> getAllVestimonialForUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		List<Vestimonial> vestimonials = new ArrayList<>();
		user.getVestimonials().forEach(vestimonials::add);
		return vestimonials;
	}

	// get one for user
}
