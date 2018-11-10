package com.vestimony.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.io.ByteStreams;
import com.vestimony.model.Image;
import com.vestimony.repository.ImageRepository;

@Service
public class ImageService {

	private static String UPLOAD_ROOT = "upload-dir";

	private ImageRepository imageRepository;
	private ResourceLoader resourceLoader;

	@Autowired
	public ImageService(ImageRepository imageRepository, ResourceLoader resourceLoader) {
		this.imageRepository = imageRepository;
		this.resourceLoader = resourceLoader;
	}

	public ResourceLoader findOneImage(String filename) {
		return (ResourceLoader) resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename);
	}

	public Image getImage(long imageId) {
		return imageRepository.findById(imageId).get();
	}

	public void createImage(MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			byte[] pic = ByteStreams.toByteArray(file.getInputStream());
			imageRepository.save(new Image(file.getOriginalFilename(), "anytype", pic));
		}
	}

	public void deleteImage(String filename) throws IOException {
		final Image byName = imageRepository.findByImageName(filename);
		imageRepository.delete(byName);
		Files.deleteIfExists(Paths.get(UPLOAD_ROOT, filename));
	}
}
