	package com.roy.upload;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
	 
	@ResponseBody
	@RequestMapping(value="/image/{img}", method= RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable("img") String img) {
		try {
			File image = new File("images/" + img);
			return ResponseEntity.ok().lastModified(image.lastModified()).cacheControl(CacheControl.maxAge(259200, TimeUnit.SECONDS)).body(FileUtils.readFileToByteArray(image));
		} catch( Exception ex) {
			return null;
		}
	}
	

	/**
	 * Upload single file using Spring Controller
	 * return strings represent the error type.
	 * server -> problem with uploading to server
	 * type -> file type is not permitted
	 * empty -> file is empty
	 */
	
	@ResponseBody
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public String uploadFileHandler( @RequestParam("file") MultipartFile file) {
		List<String> contentTypes = Arrays.asList("image/jpg", "image/jpeg", "image/gif", "image/png");
		if (!file.isEmpty()) {
			if (contentTypes.contains(file.getContentType()) ) {
				try {
					
					File serverFile = new File("tmp_images/" + UUID.randomUUID().toString().substring(0,6).replaceAll("-",  "") + "_" + System.currentTimeMillis());
					File tempFile = new File("tmp_images/" + UUID.randomUUID().toString().substring(0,6).replaceAll("-",  "") + "_" + System.currentTimeMillis());
					if (!tempFile.isDirectory()) {
						tempFile.getParentFile().mkdirs();
					}
					
					tempFile.createNewFile();
					serverFile.createNewFile();
					byte[] bytes = file.getBytes();
					
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(tempFile));
					stream.write(bytes);
					stream.close();
					
					BufferedImage image = ImageIO.read(tempFile);
					OutputStream os = new FileOutputStream(serverFile);
					
					Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("jpg");
					ImageWriter writer = (ImageWriter) writers.next();
					
				    ImageOutputStream ios = ImageIO.createImageOutputStream(os);
				    writer.setOutput(ios);
				    
				    ImageWriteParam param = writer.getDefaultWriteParam();
				    
				    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				    param.setCompressionQuality(0.7f);
				    writer.write(null, new IIOImage(image, null, null), param);

				    os.close();
				    ios.close();
				    writer.dispose();

					
					return serverFile.getName();
				} catch (Exception e) {
					return "server";
				}
			} else {
				// File Type Problem
				return "type";
			}
		} else {
			// File Is Empty
			return "empty";
		}
	}
}
