package com.vestimony.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	public Post createPost(Post post) throws IOException {
		//set user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		post.setApplicationUser(user);
		postRepository.save(post);
		return post;
	}
	
	//get all
	public List<Post> getAllPosts(){
		List<Post> posts = new ArrayList<>();
		postRepository.findAll().forEach(posts::add);
		return posts;
	}
	
	//get one by id
	public Optional<Post> getPost(long postId){
		return postRepository.findById(postId);
	}
	
	//delete posts with no review linked
	public void deleteUnfinishedPosts() {
		
	}
	//view one post
	public Optional<Post> viewPost(long postId) {
		return postRepository.findById(postId);
	}

	public String likePost(long postId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		Post post = postRepository.findById(postId).get();
		Set<ApplicationUser> likes = post.getLikes();
		likes.add(user);
		post.setLikes(likes);
		
		//increase num likes
		long numLikes = post.getNumLikes();
		numLikes++;
		post.setNumLikes(numLikes);
		
		//save
		postRepository.save(post);
	
		
		//add to users likes
		Set<Post> likedPosts = user.getLikedPost();
		likedPosts.add(post);
		user.setLikedPost(likedPosts);
		applicationUserRepository.save(user);
		
		return "Post liked";
		
		
	}

	public boolean islikedPost(long postId) {
		//get user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		
		Post post = postRepository.findById(postId).get();
		
		//check if post is liked
		Set<Post> likedPosts = user.getLikedPost();
		if(likedPosts.contains(post)) {
			return true;
		};
		return false;
	}

	public String unlikePost(long postId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser user = applicationUserRepository.findByUsername(auth.getName());
		
		Post post = postRepository.findById(postId).get();
		/*Set<ApplicationUser> likes = post.getLikes();
		likes.remove(user);
		post.setLikes(likes);*/
		
		//increase num likes
		long numLikes = post.getNumLikes();
		numLikes--;
		post.setNumLikes(numLikes);
		
		//save
		postRepository.save(post);
	
		
		//add to users likes
		Set<Post> likedPosts = user.getLikedPost();
		likedPosts.remove(post);
		user.setLikedPost(likedPosts);
		applicationUserRepository.save(user);
		
		return "Post unliked";
	}


	
	
	
	
	
	
	

	
	
	
	
	
	
	
}
