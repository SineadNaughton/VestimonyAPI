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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long postId;

	private String postInfo;
	private long userId;

	private long numLikes;

	@Column
	@CreationTimestamp
	private LocalDateTime createdDateTime;

	@ManyToOne
	@JoinColumn(name = "FkApplicationUser")
	private ApplicationUser applicationUser;

	@ManyToMany(fetch = FetchType.LAZY )
	@JoinTable(name = "Post_Vestimonial_Link", joinColumns = { @JoinColumn(name = "postId") }, inverseJoinColumns = {
			@JoinColumn(name = "vestimonialId") })
	private Set<Vestimonial> vestimonials = new HashSet<Vestimonial>();
	
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Image> images = new HashSet<Image>();
	
	
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade=CascadeType.PERSIST,
            mappedBy = "likedPost")
	private Set<ApplicationUser> likes = new HashSet<ApplicationUser>();
	
	
	


	public Post() {
		super();
	}

	public Post(String postInfo, ApplicationUser applicationUser) {
		super();
		this.postInfo = postInfo;
		this.applicationUser = applicationUser;

	}
	
	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getPostInfo() {
		return postInfo;
	}

	public void setPostInfo(String postInfo) {
		this.postInfo = postInfo;
	}

	public long getNumLikes() {
		return numLikes;
	}

	public void setNumLikes(long numLikes) {
		this.numLikes = numLikes;
	}

	@JsonIgnore
	public ApplicationUser getApplicationUser() {
		return applicationUser;
	}

	public void setApplicationUser(ApplicationUser applicationUser) {
		this.applicationUser = applicationUser;
	}

	public Set<Vestimonial> getVestimonials() {
		return vestimonials;
	}

	public void setVestimonials(Set<Vestimonial> vestimonials) {
		this.vestimonials = vestimonials;
	}
	
	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Set<ApplicationUser> getLikes() {
		return likes;
	}

	public void setLikes(Set<ApplicationUser> likes) {
		this.likes = likes;
	}
	
	
	
	
	
	

}
