package com.vestimony.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vestimony.model.Post;
import com.vestimony.service.ImageService;
import com.vestimony.service.PostService;

@RestController
@RequestMapping(value = "/vestimony/posts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private ImageService imageService;
	
	//image upload
	@PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String handlerFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		imageService.createImage(file);
		return "Sucess"+ file.getOriginalFilename();
	}
	
	//image getter
	@GetMapping(value = "/image/{imageId}/{imageName}", consumes = MediaType.ALL_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getImage(@PathVariable long imageId) {
		return imageService.getImage(imageId).getPic();
	}
/*
	// create
	@PostMapping
	@ResponseBody
	public ResponseEntity<Post> createPost(@RequestBody Post post) {
		postService.createPost(post);
		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}
*/
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
