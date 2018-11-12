package com.vestimony.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.io.ByteStreams;
import com.vestimony.model.Image;
import com.vestimony.model.Post;
import com.vestimony.repository.ImageRepository;
import com.vestimony.repository.PostRepository;

@Service
public class ImageService {


	@Autowired
	private ImageRepository imageRepository;
	
	

	@Autowired
	private PostRepository postRepository;

	public Image getImage(long imageId) {
		return imageRepository.findById(imageId).get();
	}

	public void createImage(MultipartFile file, long postId) throws IOException {
		Post post = postRepository.findById(postId).get();
		if (!file.isEmpty()) {
			byte[] pic = ByteStreams.toByteArray(file.getInputStream());
			Image image = new Image(file.getOriginalFilename(), "anytype", pic, post);
			imageRepository.save(image);
			
			//associate image to post
			Set<Image> images = post.getImages();
			images.add(image);
			post.setImages(images);
			postRepository.save(post);
		}
	}

	/*public void deleteImage(String filename) throws IOException {
		final Image byName = imageRepository.findByImageName(filename);
		imageRepository.delete(byName);
		Files.deleteIfExists(Paths.get(UPLOAD_ROOT, filename));
	}*/
}
