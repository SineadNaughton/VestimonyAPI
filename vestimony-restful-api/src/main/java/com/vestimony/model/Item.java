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
	private String image;
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

	public Item() {
		super();
	}

	public Item(String name, String colour, double price, String brand, String url, String image) {
		super();
		this.name = name;
		this.colour = colour;
		this.price = price;
		this.brand = brand;
		this.url = url;
		this.image = image;
	}

	public Item(long itemId, String name, String colour, double price, String brand, String url, String image,
			String category, int sizeAdjustment, int rating, long numReviews, long numSaved) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.colour = colour;
		this.price = price;
		this.brand = brand;
		this.url = url;
		this.image = image;
		this.category = category;
		this.sizeAdjustment = sizeAdjustment;
		this.rating = rating;
		this.numReviews = numReviews;
		this.numSaved = numSaved;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void decideItemCategory(Item item) {
		String name = item.getName().toLowerCase();
		if (name.contains("top") || name.contains("bodysuit") || name.contains("shirt") || name.contains("blouse")
				|| name.contains("vest") || name.contains("cami") || name.contains("tee") || name.contains("tank")) {
			item.setCategory("top");
		} else if (name.contains("trouser") || name.contains("pant") || name.contains("legging")
				|| name.contains("shorts")) {
			item.setCategory("trouser");
		} else if (name.contains("skirt")) {
			item.setCategory("skirt");
		} else if (name.contains("dress")) {
			item.setCategory("dress");
		} else if (name.contains("jumper") || name.contains("cardi") || name.contains("polo")
				|| name.contains("sweater") || name.contains("knit")) {
			item.setCategory("knitwear");
		} else if (name.contains("bag") || name.contains("clutch") || name.contains("purse") || name.contains("tote")
				|| name.contains("wallet")) {
			item.setCategory("bag");
		} else if (name.contains("boot") || name.contains("heel") || name.contains("shoe") || name.contains("pump")
				|| name.contains("trainer")) {
			item.setCategory("shoe");
		} else {
			item.setCategory("other");
		}
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
	
	

}
