package com.vestimony.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Post;
import com.vestimony.repository.ApplicationUserRespository;
import com.vestimony.repository.PostRepository;
@Service
public class LikePostService {

	@Autowired
	private ApplicationUserRespository applicationUserRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	public String likePost(ApplicationUser user, Post post) {
		Set<Post> likedPosts = user.getLikedPost();
		likedPosts.add(post);
		user.setLikedPost(likedPosts);
		applicationUserRepository.save(user);
		//increase saved
		long numLikes = post.getNumLikes();
		numLikes++;
		post.setNumLikes(numLikes);
		postRepository.save(post);
		return "Post Liked";
	}
	
	public String unlikePost(ApplicationUser user, Post post) {
		Set<Post> likedPosts = user.getLikedPost();
		likedPosts.remove(post);
		user.setLikedPost(likedPosts);
		applicationUserRepository.save(user);
		//increase saved
		long numLikes = post.getNumLikes();
		numLikes--;
		post.setNumLikes(numLikes);
		postRepository.save(post);
		return "Post unliked";
	}
	
	// check if post liked
	public boolean islikedPost(ApplicationUser user, Post post) {
		Set<Post> likedPosts = user.getLikedPost();
		if (likedPosts.contains(post)) {
			return true;
		}
		return false;
	}
}
