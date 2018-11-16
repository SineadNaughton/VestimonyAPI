package com.vestimony.model;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Vestimonial implements Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long vestimonyId;
	private String comments;
	private int sizeBought;
	private int qualityForPriceRating;
	private int rating;
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
	
	

	public Vestimonial(long vestimonyId, String comments, int sizeBought, int qualityForPriceRating, int rating,
			Item item, ApplicationUser applicationUser) {
		super();
		this.vestimonyId = vestimonyId;
		this.comments = comments;
		this.sizeBought = sizeBought;
		this.qualityForPriceRating = qualityForPriceRating;
		this.rating = rating;
		this.item = item;
		this.applicationUser = applicationUser;
	}
	
	



	public Vestimonial(String comments, int sizeBought, int qualityForPriceRating, int rating, Item item,
			ApplicationUser applicationUser) {
		super();
		this.comments = comments;
		this.sizeBought = sizeBought;
		this.qualityForPriceRating = qualityForPriceRating;
		this.rating = rating;
		this.item = item;
		this.applicationUser = applicationUser;
	}



	public long getVestimonyId() {
		return vestimonyId;
	}

	public void setVestimonyId(long vestimonyId) {
		this.vestimonyId = vestimonyId;
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

	public int getQualityForPriceRating() {
		return qualityForPriceRating;
	}

	public void setQualityForPriceRating(int qualityForPriceRating) {
		this.qualityForPriceRating = qualityForPriceRating;
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
	
	

}
