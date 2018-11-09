package com.vestimony.model;

import java.util.Set;

public class UserProfile {

	private String username;
	
	private int height;

	private int sizeTop;

	private int sizeBottom;

	private String bio;
	
	private Set<Post> posts;

	public UserProfile() {

	}

	public UserProfile(ApplicationUser appUser) {
		super();
		this.username = appUser.getUsername();
		this.height = appUser.getHeight();
		this.sizeTop = appUser.getSizeTop();
		this.sizeBottom = appUser.getSizeBottom();
		this.bio = appUser.getBio();
		this.posts=appUser.getPosts();
	}

	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSizeTop() {
		return sizeTop;
	}

	public void setSizeTop(int sizeTop) {
		this.sizeTop = sizeTop;
	}

	public int getSizeBottom() {
		return sizeBottom;
	}

	public void setSizeBottom(int sizeBottom) {
		this.sizeBottom = sizeBottom;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
	
	

}
