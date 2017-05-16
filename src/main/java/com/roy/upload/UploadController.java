	package com.roy.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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
	public byte[] getImage(@PathVariable("img") String img) {
		try {
			File image = new File("images/" + img);
			return FileUtils.readFileToByteArray(image);
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
					String filename = FilenameUtils.removeExtension(file.getOriginalFilename());
					byte[] bytes = file.getBytes();
					File serverFile = new File("tmp_images/" + UUID.randomUUID().toString().substring(0,6).replaceAll("-",  "") + "_" + System.currentTimeMillis());
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
						
					return serverFile.getName();
				} catch (Exception e) {
					// Server Problem
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
