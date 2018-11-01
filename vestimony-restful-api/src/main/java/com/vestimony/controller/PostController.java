package com.vestimony.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vestimony.model.Post;
import com.vestimony.model.Vestimonial;
import com.vestimony.service.PostService;


@RestController
public class PostController {

	
	@Autowired
	private PostService postService;
	
	
	//create
	@PostMapping(value="/posts")
	public void createPost(@RequestBody Post post ) {
		postService.createPost(post);
	}
	
	//view with vestimonials
	@GetMapping(value="/posts/{postId}")
	public Optional<Post> viewPost(@PathVariable("postId") long postId ) {
		return postService.viewPost(postId);
	}
	
}
