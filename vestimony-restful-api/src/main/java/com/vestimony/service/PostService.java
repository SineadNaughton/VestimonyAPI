package com.vestimony.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Item;
import com.vestimony.model.Post;
import com.vestimony.model.Vestimonial;
import com.vestimony.repository.ApplicationUserRespository;
import com.vestimony.repository.PostRepository;
import com.vestimony.repository.VestimonialRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ApplicationUserRespository applicationUserRepository;
	
	@Autowired
	private VestimonialRepository vestimonialRespository;
	
	//create
	public void createPost(Post post) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		post.setApplicationUser(user);
		postRepository.save(post);
	}
	
	//delete posts with no review linked
	public void deleteUnfinishedPosts() {
		
	}
	//view one post
	public Optional<Post> viewPost(long postId) {
		return postRepository.findById(postId);
	}
	
	//get one post for user
	
	
	//get all post for user
	
	
}
