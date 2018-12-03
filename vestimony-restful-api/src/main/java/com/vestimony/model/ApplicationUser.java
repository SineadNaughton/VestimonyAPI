package com.vestimony.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class ApplicationUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long userId;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(unique = true, nullable = false)
	private String email;
	private String role;
	private int heightFeet;	
	private int heightInches;	
	private int sizeTop;
	private int sizeBottom;
	private String bio;
	private long numFollowers;
	private long linkToBuy;
	
	@Column
	@CreationTimestamp
	private LocalDateTime createdDateTime;
	
	
	@Lob
	@Column(length=1000000)
	private byte[] pic;

	@OneToMany(mappedBy = "applicationUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Vestimonial> vestimonials = new HashSet<Vestimonial>();

	@OneToMany(mappedBy = "applicationUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Post> posts = new HashSet<Post>();

	
	//following, liked, saved
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "following", joinColumns = { @JoinColumn(name = "follower") }, inverseJoinColumns = {
			@JoinColumn(name = "following") })
	private Set<ApplicationUser> following = new HashSet<ApplicationUser>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "likedPosts", joinColumns = { @JoinColumn(name = "userThatLikes") }, inverseJoinColumns = {
			@JoinColumn(name = "postLiked") })
	private Set<Post> likedPost = new HashSet<Post>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "savedItems", joinColumns = { @JoinColumn(name = "userThatSaved") }, inverseJoinColumns = {
			@JoinColumn(name = "itemSaved") })
	private Set<Item> savedItems = new HashSet<Item>();

	// constructors
	public ApplicationUser() {
	}

	public ApplicationUser(String username, String password, String email, int heightFeet, int heightInches, int sizeTop, int sizeBottom,
			String bio) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.heightFeet = heightFeet;
		this.heightInches= heightInches;
		this.sizeTop = sizeTop;
		this.sizeBottom = sizeBottom;
		this.bio = bio;
		this.role = "USER";
	}

	// methods

	public int getHeightFeet() {
		return heightFeet;
	}

	public long getLinkToBuy() {
		return linkToBuy;
	}

	public void setLinkToBuy(long linkToBuy) {
		this.linkToBuy = linkToBuy;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
	public long getNumFollowers() {
		return numFollowers;
	}

	public void setNumFollowers(long numFollowers) {
		this.numFollowers = numFollowers;
	}

	@JsonIgnore
	public Set<Vestimonial> getVestimonials() {
		return vestimonials;
	}

	public void setVestimonials(Set<Vestimonial> vestimonials) {
		this.vestimonials = vestimonials;
	}

	@JsonIgnore
	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	@JsonIgnore
	public Set<ApplicationUser> getFollowing() {
		return following;
	}

	public void setFollowing(Set<ApplicationUser> following) {
		this.following = following;
	}

	@JsonIgnore
	public Set<Post> getLikedPost() {
		return likedPost;
	}

	public void setLikedPost(Set<Post> likedPost) {
		this.likedPost = likedPost;
	}
	
	@JsonIgnore
	public Set<Item> getSavedItems() {
		return savedItems;
	}

	public void setSavedItems(Set<Item> savedItems) {
		this.savedItems = savedItems;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	
	
	
	

}
