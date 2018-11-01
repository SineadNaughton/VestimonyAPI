package com.vestimony.model;

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

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long postId;
	private String postInfo;
	private long numLikes;
	@Lob
	private byte[] postImage;
	
	@ManyToOne
	@JoinColumn(name = "FkApplicationUser")
	private ApplicationUser applicationUser;
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "Post_Vestimonial_Link",
            joinColumns = { @JoinColumn(name = "postId") },
            inverseJoinColumns = { @JoinColumn(name = "vestimonialId") })
	private Set<Vestimonial> vestimonials = new HashSet<Vestimonial>();

	
	
	
	
	public Post() {
		super();
	}
	
	
	

	public Post(String postInfo, byte[] postImage, ApplicationUser applicationUser) {
		super();
		this.postInfo = postInfo;
		this.applicationUser = applicationUser;
		this.postImage = postImage;
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




	public byte[] getPostImage() {
		return postImage;
	}




	public void setPostImage(byte[] postImage) {
		this.postImage = postImage;
	}

	
	
	
}
