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
import com.vestimony.model.Item;
import com.vestimony.model.Post;
import com.vestimony.model.Vestimonial;
import com.vestimony.service.ApplicationUserService;
import com.vestimony.service.ImageService;
import com.vestimony.service.ItemService;
import com.vestimony.service.PostService;
import com.vestimony.service.VestimonialService;

@RestController
@RequestMapping(value = "/vestimony/posts" , consumes = MediaType.APPLICATION_JSON_VALUE, produces =MediaType.APPLICATION_JSON_VALUE)
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private ApplicationUserService applicationUserService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private VestimonialService vestimonialService;

	// view all posts
	@GetMapping
	public ResponseEntity<List<Post>> getAllPosts() {
		List<Post> posts = postService.getAllPosts();
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

	// get one post
	@GetMapping(value = "/{postId}")
	public ResponseEntity<Post> getPost(@PathVariable("postId") long postId) {
		Post post = postService.getOnePost(postId).get();
		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}

	// create
	@PostMapping
	public ResponseEntity<Post> createPost(@RequestBody Post post) {
		ApplicationUser user = applicationUserService.getCurrentUser();
		postService.createPost(user, post);
		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}

	// image upload
	@PostMapping(value = "/image/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String addPostImage(@PathVariable long postId, @RequestParam("file") MultipartFile file)
			throws IOException {
		Post post = postService.getOnePost(postId).get();
		imageService.createImage(file, post);
		return "Sucess " + file.getOriginalFilename();
	}

	// image getter
	@GetMapping(value = "/image/{postId}", consumes = MediaType.ALL_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getImageForPost(@PathVariable long postId) {
		Post post = postService.getOnePost(postId).get();
		return imageService.getImageForPost(post).getPic();
	}

	// get posts for a paraticular item
	@GetMapping(value = "/foritem/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Post>> getPostsForItem(@PathVariable long itemId) {
		Item item = itemService.findById(itemId);
		List<Vestimonial> vestimonials = vestimonialService.getAllVestimonialForItem(item);
		List<Post> posts = postService.getPostsForItem(vestimonials);
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

	// get trending
	@GetMapping(value = "/trending", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Post>> getTrending() {
		List<Post> posts = postService.getTrending();
		// add to users liked posts

		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

	// GET POSTS CREATED BY USER
	@GetMapping(value = "users/{userId}")
	public ResponseEntity<List<Post>> getPostsForProfile(@PathVariable long userId) {
		ApplicationUser user = applicationUserService.getApplicationUser(userId);
		List<Post> posts = postService.getPostsForPorifle(user);
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

}
