package com.vestimony.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vestimony.model.ApplicationUser;
import com.vestimony.model.Post;
import com.vestimony.model.Vestimonial;
import com.vestimony.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	// get all
	public List<Post> getAllPosts() {
		List<Post> posts = new ArrayList<>();
		postRepository.findAll().forEach(posts::add);
		posts = this.removeIfNotFinished(posts);
		return posts;
	}

	// get one post
	public Optional<Post> getOnePost(long postId) {
		return postRepository.findById(postId);
	}

	// create
	public Post createPost(ApplicationUser user, Post post) {
		// set user
		post.setApplicationUser(user);
		postRepository.save(post);
		return post;
	}

	// GET POSTS FEATURING AN ITEM
	public List<Post> getPostsForItem(List<Vestimonial> vestimonials) {
		Set<Post> posts = new HashSet<>();
		for (Vestimonial v : vestimonials) {
			posts.addAll(v.getPosts());
		}
		List<Post> postsForItem = new ArrayList<>(posts);
		postsForItem = this.removeIfNotFinished(postsForItem);
		return postsForItem;
	}

	// trending
	public List<Post> getTrending() {
		List<Post> posts = new ArrayList<>();
		LocalDateTime localDateTimeFrom = LocalDateTime.now().minusDays(30);
		LocalDateTime localDateTimeTo = LocalDateTime.now();

		postRepository.findByCreatedDateTimeBetweenOrderByNumLikesDesc(localDateTimeFrom, localDateTimeTo)
				.forEach(posts::add);

		posts = this.removeIfNotFinished(posts);
		return posts;
	}
	
	// GET LIST OF POSTS FOR A USERS PROFILE
		public List<Post> getPostsForPorifle(ApplicationUser user) {
			List<Post> posts = new ArrayList<>();
			postRepository.findByApplicationUser(user).forEach(posts::add);
			posts = this.removeIfNotFinished(posts);
			return posts;
		}


	// GET POSTS FROM THE USERS YOU FOLLOW
	public List<Post> getFollowingPosts(ApplicationUser user) {
		// get list following
		Set<ApplicationUser> usersFollowing = user.getFollowing();
		// list of post
		List<Post> followedPosts = new ArrayList<>();
		for (ApplicationUser followed : usersFollowing) {
			postRepository.findByApplicationUserOrderByCreatedDateTimeDesc(followed).forEach(followedPosts::add);
		}
		followedPosts = this.removeIfNotFinished(followedPosts);
		return followedPosts;
	}


	// remove if no picture or vestimonial
	public List<Post> removeIfNotFinished(List<Post> posts) {
		for (Iterator<Post> iterator = posts.iterator(); iterator.hasNext();) {
			Post post = iterator.next();
			if (!this.hasRequired(post)) {
				iterator.remove();
			}
		}
		return posts;

	}

	// check image and vestimonial
	boolean hasRequired(Post post) {
		if (post.getImages().size() > 0 && post.getVestimonials().size() > 0) {
			return true;
		}
		return false;
	}

}
