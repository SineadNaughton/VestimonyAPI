package com.vestimony.model;

import java.io.Serializable;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Vestimonial implements Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long vestimonialId;
	private String comments;
	private int sizeBought;
	private int usualSize;
	private int rating;
	
	@Column
	@CreationTimestamp
	private LocalDateTime createdDateTime;
	
	
	@ManyToOne
	@JoinColumn(name = "FkItemId")
	private Item item;
	
	@ManyToOne
	@JoinColumn(name = "FkApplicationUser")
	private ApplicationUser applicationUser;
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "vestimonials")
	private Set<Post> posts = new HashSet<Post>();

	
	
	
	
	public Vestimonial() {
		super();
	}
	

	public Vestimonial(String comments, int sizeBought, int usualSize, int rating) {
		super();
		this.comments = comments;
		this.sizeBought = sizeBought;
		this.usualSize=usualSize;
		this.rating = rating;
		
	}



	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getSizeBought() {
		return sizeBought;
	}

	public void setSizeBought(int sizeBought) {
		this.sizeBought = sizeBought;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public ApplicationUser getApplicationUser() {
		return applicationUser;
	}

	public void setApplicationUser(ApplicationUser applicationUser) {
		this.applicationUser = applicationUser;
	}


	@JsonIgnore
	public Set<Post> getPosts() {
		return posts;
	}



	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}



	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}



	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}



	public long getVestimonialId() {
		return vestimonialId;
	}



	public void setVestimonialId(long vestimonialId) {
		this.vestimonialId = vestimonialId;
	}



	public int getUsualSize() {
		return usualSize;
	}



	public void setUsualSize(int usualSize) {
		this.usualSize = usualSize;
	}
	
	
	
	

}
