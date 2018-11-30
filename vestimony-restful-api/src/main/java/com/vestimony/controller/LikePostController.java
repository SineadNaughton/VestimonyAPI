package com.vestimony.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Post;
import com.vestimony.service.ApplicationUserService;
import com.vestimony.service.LikePostService;
import com.vestimony.service.PostService;
@RestController
@RequestMapping(value = "/vestimony/posts/liked", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class LikePostController {

	@Autowired
	private ApplicationUserService applicationUserService;
	@Autowired
	private PostService postService;
	@Autowired
	private LikePostService likePostService;
	
	// GET USER'S LIKED POSTS
		@GetMapping
		public ResponseEntity<List<Post>> getLikedPosts() {
			List<Post> posts = applicationUserService.getLikedPosts();
			posts = postService.removeIfNotFinished(posts);
			return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		}
		
		//like post
		@PostMapping(value="/{postId}", produces = MediaType.TEXT_PLAIN_VALUE)
		public ResponseEntity<String> likePost(@PathVariable long postId) {
			ApplicationUser user = applicationUserService.getCurrentUser();
			Post post = postService.getOnePost(postId).get();
			String resp = likePostService.likePost(user, post);
			return new ResponseEntity<String>(resp, HttpStatus.OK);
		}
		
		//unlike
		@DeleteMapping(value = "/{postId}", produces = MediaType.TEXT_PLAIN_VALUE)
		public ResponseEntity<String> unlikePost(@PathVariable long postId) {
			ApplicationUser user = applicationUserService.getCurrentUser();
			Post post = postService.getOnePost(postId).get();
			String resp = likePostService.unlikePost(user, post);
			return new ResponseEntity<String>(resp, HttpStatus.OK);
		}
		
		
		
		//check if post liked
		@GetMapping(value="/{postId}/isliked")
		public ResponseEntity<Boolean> islikedPost(@PathVariable long postId) {
			ApplicationUser user = applicationUserService.getCurrentUser();
			Post post = postService.getOnePost(postId).get();
			boolean isLiked = likePostService.islikedPost(user, post);
			return new ResponseEntity<Boolean>(isLiked, HttpStatus.OK);
		}
}
