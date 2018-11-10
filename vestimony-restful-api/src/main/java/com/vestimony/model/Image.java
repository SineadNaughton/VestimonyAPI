package com.vestimony.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Image {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
    private Long imageId;
	
   
	private String imageName;
    
    
	private String imageType;
	
	@Lob
    private byte[] pic;
	
	@ManyToOne
	@JoinColumn(name = "FkPost")
	private Post post;
	
	@ManyToOne
	@JoinColumn(name = "FkItem")
	private Item item;

	public Image() {
		super();
	}
	
	

	public Image(String imageName) {
		super();
		this.imageName = imageName;
	}

	public Image(String imageName, String imageType, byte[] pic) {
		super();
		this.imageName = imageName;
		this.imageType = imageType;
		this.pic = pic;
	}
	
	

	public Image(Long imageId, String imageName, String imageType, byte[] pic) {
		super();
		this.imageId = imageId;
		this.imageName = imageName;
		this.imageType = imageType;
		this.pic = pic;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}
	
	

}
