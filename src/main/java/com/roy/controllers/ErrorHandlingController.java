package com.roy.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.roy.models.Search;

@Controller
public class ErrorHandlingController implements ErrorController {
		
	private static final String PATH = "/error";
	
	@RequestMapping(value = PATH)
	public String error(Model model) {
		model.addAttribute("search", new Search());
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("static/photos/error_img_slider").getFile());
			
			final String img_folder = "photos/error_img_slider/";
			
			List<String> images = new ArrayList<String>();
			
			for (File img : file.listFiles() ) {
				images.add(img_folder + img.getName());
			}
			
			if (images.size() != 0) {
				model.addAttribute("images", images);
			}
			
		}catch(Exception ex){}
		
		return "/error/404";
	}
	
	@Override
	public String getErrorPath() {
		return PATH;
	}
}
