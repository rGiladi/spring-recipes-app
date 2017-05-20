package com.roy.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.roy.models.Search;
import com.roy.services.RecipeService;
import com.roy.services.MenusService;

@Controller
public class AdminPanelController {
	
	@Autowired
	private RecipeService recipesService;
	

	@Autowired 
	private MenusService menusService;
	
	
	@RequestMapping(value= "/admin/panel", method=RequestMethod.GET)
	public String adminPanel(Model model) {
		model.addAttribute("search", new Search());
		model.addAttribute("recipes_size", recipesService.getAllReportedRecipes().size());
		model.addAttribute("menus_size", menusService.getAllReportedMenus().size());
		return "admin/panel";
	}
}
