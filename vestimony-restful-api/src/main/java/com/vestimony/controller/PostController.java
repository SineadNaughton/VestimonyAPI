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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Post;
import com.vestimony.model.Vestimonial;
import com.vestimony.service.ImageService;
import com.vestimony.service.PostService;

@RestController
@RequestMapping(value = "/vestimony/posts") //, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private ImageService imageService;
	
	//image upload
	@PostMapping(value = "/image/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String handlerFileUpload(@PathVariable long postId, @RequestParam("file") MultipartFile file) throws IOException {
		imageService.createImage(file, postId);
		return "Sucess "+ file.getOriginalFilename();
	}
	
	//image getter
	@GetMapping(value = "/image/{postId}", consumes = MediaType.ALL_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getImageForPost(@PathVariable long postId) {
		return imageService.getImageForPost(postId).getPic();
	}

	// create
	@PostMapping
	public ResponseEntity<Post> createPost(@RequestBody Post post) throws IOException {
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
		Post post = postService.viewPost(postId).get();
		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}
	
	//like post
	@GetMapping(value="/{postId}/like", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> likePost(@PathVariable long postId) {
		String resp = postService.likePost(postId);
		//add to users liked posts
		
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}
	
	//unlike
	@GetMapping(value = "/{postId}/unlike", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> unlikePost(@PathVariable long postId) {
		String resp = postService.unlikePost(postId);
		//add to users liked posts
		
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}
	
	//show liked
	@GetMapping(value="/{postId}/isliked")
	public ResponseEntity<Boolean> islikedPost(@PathVariable long postId) {
		boolean isLiked = postService.islikedPost(postId);
		//add to users liked posts
		
		return new ResponseEntity<Boolean>(isLiked, HttpStatus.OK);
	}
	
	
	//get posts for a paraticular item
	@GetMapping(value="/foritem/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Post>> getPostsForItem(@PathVariable long itemId) {
		List<Post> posts = postService.getPostsForItem(itemId);
		//add to users liked posts
		
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}
	
	//get trending
	@GetMapping(value="/trending", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Post>> getTrending() {
		List<Post> posts = postService.getTrending();
		//add to users liked posts
		
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}
	



}
