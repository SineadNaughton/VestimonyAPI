package com.vestimony.model;

import java.io.Serializable;
import javax.persistence.JoinColumn;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class ApplicationUser implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserID", unique = true)
	private long userId;

	@Column(name = "Username", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "Email", unique = true, nullable = false)
	private String email;
	// @Column(name = "RoleAdmin")
	// private boolean roleAdmin;
	@Column(name = "Height")
	private int height;

	@Column(name = "SizeTop")
	private int sizeTop;

	@Column(name = "SizeBottom")
	private int sizeBottom;

	@Column(name = "Bio")
	private String bio;

	@OneToMany(mappedBy = "applicationUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Vestimonial> vestimonials = new HashSet<Vestimonial>();

	@OneToMany(mappedBy = "applicationUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Post> posts = new HashSet<Post>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "following", joinColumns = { @JoinColumn(name = "follower") }, inverseJoinColumns = {
			@JoinColumn(name = "following") })
	private Set<ApplicationUser> following = new HashSet<ApplicationUser>();

	// constructors

	public ApplicationUser() {
	}

	public ApplicationUser(String username, String password, String email, int height, int sizeTop, int sizeBottom,
			String bio) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.height = height;
		this.sizeTop = sizeTop;
		this.sizeBottom = sizeBottom;
		this.bio = bio;
	}

	public ApplicationUser(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public ApplicationUser(long userId, String username, String password, String email, int height, int sizeTop,
			int sizeBottom, String bio) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.height = height;
		this.sizeTop = sizeTop;
		this.sizeBottom = sizeBottom;
		this.bio = bio;
	}

	// methods

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
	
	

}
