package com.roy.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.roy.models.Search;

@ControllerAdvice
public class GlobalModels {
	
	String version = "0.1";
	@ModelAttribute
	public void globalAttributes(Model model) {
		model.addAttribute("search", new Search());
		model.addAttribute("static_version", version);
	}
}
