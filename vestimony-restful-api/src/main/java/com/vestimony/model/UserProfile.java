package com.vestimony.model;

import java.util.Set;

public class UserProfile {

	private String username;
	private long userId;
	private int heightFeet;
	private int heightInches;
	private long numFollowers;

	private int sizeTop;

	private int sizeBottom;

	private String bio;
	
	private Set<Post> posts;

	public UserProfile() {

	}

	public UserProfile(ApplicationUser appUser) {
		super();
		this.userId =appUser.getUserId();
		this.username = appUser.getUsername();
		this.heightFeet = appUser.getHeightFeet();
		this.heightInches = appUser.getHeightInches();
		this.sizeTop = appUser.getSizeTop();
		this.sizeBottom = appUser.getSizeBottom();
		this.bio = appUser.getBio();
		this.posts=appUser.getPosts();
		this.userId = appUser.getUserId();
		this.numFollowers = appUser.getNumFollowers();
	}

	
	

	public long getNumFollowers() {
		return numFollowers;
	}

	public void setNumFollowers(long numFollowers) {
		this.numFollowers = numFollowers;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getHeightFeet() {
		return heightFeet;
	}

	public void setHeightFeet(int heightFeet) {
		this.heightFeet = heightFeet;
	}
	
	

	public int getHeightInches() {
		return heightInches;
	}

	public void setHeightInches(int heightInches) {
		this.heightInches = heightInches;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
	
	

}
