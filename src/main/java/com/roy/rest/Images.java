package com.roy.rest;

import java.io.File;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Images {
	
	@ResponseBody
	@RequestMapping(value="/storage_images", method=RequestMethod.GET) 
	public String getImagesNames() {
		StringBuffer sb = new StringBuffer();
		try {
			File images = new File("images");
			for (File image : images.listFiles()) {
				sb.append(image.getName());
				sb.append('\n');
			}
		} catch(Exception ex) {
			
		}
		return sb.toString();
	}
}
