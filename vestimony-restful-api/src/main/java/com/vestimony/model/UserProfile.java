package com.vestimony.model;

public class UserProfile {

	private int height;

	private int sizeTop;

	private int sizeBottom;

	private String bio;

	public UserProfile() {

	}

	public UserProfile(ApplicationUser appUser) {
		super();
		this.height = appUser.getHeight();
		this.sizeTop = appUser.getSizeTop();
		this.sizeBottom = appUser.getSizeBottom();
		this.bio = appUser.getBio();
	}

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

}
