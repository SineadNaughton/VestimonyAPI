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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class Item implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private long itemId;
	private String name;
	private String colour;
	private double price;
	private String brand;
	@Column(unique = true)
	private String url;
	private String imageUrl;
	@Lob
	private byte[] pic;
	private String category;
	private int sizeAdjustment;
	private int rating;
	private long numReviews;
	private long numSaved;
	@Column
	@CreationTimestamp
	private LocalDateTime createDateTime;
	
	@OneToMany(mappedBy="item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Vestimonial> vestimonials = new HashSet<Vestimonial>();
	
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Image> images = new HashSet<Image>();
	
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "savedItems")
	private Set<ApplicationUser> saves = new HashSet<ApplicationUser>();

	public Item() {
		super();
	}

	public Item(String name, String colour, double price, String brand, String url, String imageUrl, String category, byte[] pic) {
		super();
		this.name = name;
		this.colour = colour;
		this.price = price;
		this.brand = brand;
		this.url = url;
		this.imageUrl = imageUrl;
		this.category=category;
		this.pic=pic;
	}



	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	

	public int getSizeAdjustment() {
		return sizeAdjustment;
	}

	public void setSizeAdjustment(int sizeAdjustment) {
		this.sizeAdjustment = sizeAdjustment;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public long getNumReviews() {
		return numReviews;
	}

	public void setNumReviews(long numReviews) {
		this.numReviews = numReviews;
	}

	public long getNumSaved() {
		return numSaved;
	}

	public void setNumSaved(long numSaved) {
		this.numSaved = numSaved;
	}

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}
	@JsonIgnore
	public Set<Vestimonial> getVestimonials() {
		return vestimonials;
	}

	public void setVestimonials(Set<Vestimonial> vestimonials) {
		this.vestimonials = vestimonials;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Set<ApplicationUser> getSaves() {
		return saves;
	}

	public void setSaves(Set<ApplicationUser> saves) {
		this.saves = saves;
	}
	
	
	
	

}
