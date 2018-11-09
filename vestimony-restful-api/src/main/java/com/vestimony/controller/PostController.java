package com.vestimony.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vestimony.model.Post;
import com.vestimony.model.Vestimonial;
import com.vestimony.service.PostService;

@RestController
@RequestMapping(value = "/vestimony/posts", consumes = "application/json", produces = "application/json")
public class PostController {

	@Autowired
	private PostService postService;

	// create
	@PostMapping
	public ResponseEntity<Post> createPost(@RequestBody Post post) {
		postService.createPost(post);
		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}

	// view all posts
	@GetMapping
	public ResponseEntity<List<Post>> getAllPosts() {
		List<Post> posts = postService.getAllPosts();
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

	// get one post
	@GetMapping(value = "/{postId}")
	public ResponseEntity<Post> viewPost(@PathVariable("postId") long postId) {
		Post post = postService.getPost(postId).get();
		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}


}
