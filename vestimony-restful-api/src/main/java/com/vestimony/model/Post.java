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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long postId;

	private String postInfo;

	private long numLikes;


	
	@Column
	@CreationTimestamp
	private LocalDateTime createDateTime;

	@ManyToOne
	@JoinColumn(name = "FkApplicationUser")
	private ApplicationUser applicationUser;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "Post_Vestimonial_Link", joinColumns = { @JoinColumn(name = "postId") }, inverseJoinColumns = {
			@JoinColumn(name = "vestimonialId") })
	private Set<Vestimonial> vestimonials = new HashSet<Vestimonial>();
	
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Image> images = new HashSet<Image>();


	public Post() {
		super();
	}

	public Post(String postInfo, Set<Image> images, ApplicationUser applicationUser) {
		super();
		this.postInfo = postInfo;
		this.applicationUser = applicationUser;
		this.images = images;
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


	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}
	
	
	
	

}
