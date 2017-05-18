package com.roy.admin;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.roy.models.Recipe;
import com.roy.models.Search;
import com.roy.services.RecipeService;
import com.roy.services.MenusService;

@Controller
public class AdminReportsController {
	
	@Autowired
	private RecipeService recipesService;
	
	@Autowired
	private MenusService menusService;
	
	@RequestMapping(value="/admin/reports/recipes", method=RequestMethod.GET)
	public String adminReportsRecipes(Model model) {
		model.addAttribute("search", new Search());
		model.addAttribute("recipes", recipesService.getAllReportedRecipes());
		model.addAttribute("is_recipes", true);
		return "admin/reports";
	}
	
	@RequestMapping(value="/admin/reports/delete", method=RequestMethod.POST)
	@ResponseBody public String delete(@RequestParam(value="m") int m, @RequestParam(value="id") long id) {
		/*
		 * m ->
		 * 1 = menu
		 * 2 = recipe
		 */
		
		if (m == 1) {
			menusService.deleteMenu(id);
		}
		else {
			Recipe recipe = recipesService.findRecipeById(id);
			File img = new File("/images/" + recipe.getImage());
			FileUtils.deleteQuietly(img);
			recipesService.deleteRecipe(id);
		}
		
		return "";
	}
	
	@RequestMapping(value="/admin/reports/menus", method=RequestMethod.GET)
	public String adminReportsMenus(Model model) {
		model.addAttribute("search", new Search());
		model.addAttribute("menus", menusService.getAllReportedMenus());
		model.addAttribute("is_menus", true);
		return "admin/reports";
	}

	
}
