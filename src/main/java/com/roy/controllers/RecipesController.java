package com.roy.controllers;

import java.io.File;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.roy.models.Recipe;
import com.roy.models.Search;
import com.roy.models.User;
import com.roy.repositories.RecipeRepository;
import com.roy.security.Services.UserService;
import com.roy.services.IngridientsCategoryService;
import com.roy.services.RecipeService;
import com.roy.validation.RecipesValidation;

@Controller
public class RecipesController{
	
	private RecipeService recipesService;
	private UserService userService;
	private RecipeRepository recipesRepo;
	private IngridientsCategoryService ingCatService;
	
	@Autowired 
	public RecipesController(RecipeService recipes, UserService userService, RecipeRepository recipesRepo, IngridientsCategoryService ingCatService) {
		this.recipesService = recipes;
		this.userService = userService;
		this.recipesRepo = recipesRepo;
		this.ingCatService = ingCatService;
	}
	
	
	@RequestMapping(value="/", method=RequestMethod.GET) 
	public String newWeb(Model model, Principal principal) {
		try { 
			long id = userService.findByUsername(principal.getName()).getId();
			model.addAttribute("user_id", id);
			
		}catch (Exception ex) {	}
		
		if (!recipesRepo.AllRecipesPaging(21).isEmpty()) { // If there are more pages to show
			model.addAttribute("more_recipes", true);
		}
		
		model.addAttribute("recipe", new Recipe());
		model.addAttribute("recipes", recipesRepo.AllRecipesPaging(0));
		addPhotosModel(model);
		return "recipes";
	}
	@RequestMapping(value="/page/{n}", method=RequestMethod.GET)
	public String newWebRecipePagination(@PathVariable int n, Principal principal, Model model) {
		if (n > 1) {
			try { 
				long id = userService.findByUsername(principal.getName()).getId();
				model.addAttribute("user_id", id);
				
			}catch (Exception ex) {	}
			
			/*
			 * Ex: 
			 * n = 2
			 * n * 20 - 20 = 2 * 20 - 20 = 20
			 * n * 20 = 2 * 20 = 40
			 * 
			 * This is our second page on recipes pagination.
			 */
			
			List<Recipe> recipes = recipesRepo.AllRecipesPaging(n * 20 - 19);
			
			if (recipes.isEmpty()) {
				return "redirect:/no-more-recipes";
			}
			
			if (!recipesRepo.AllRecipesPaging(n * 20 + 1).isEmpty()) { // If there are more pages to show
				model.addAttribute("more_page", n+1);
			}
			
			model.addAttribute("page", n);
			model.addAttribute("recipes", recipes);
			model.addAttribute("recipe", new Recipe());
			
			return "recipes_paging";
		}
		
		return "redirect:/error";
	}
	
	@RequestMapping(value="/addrecipe", method=RequestMethod.GET)
	public String newRecipe(Recipe recipe, Model model) {
		model.addAttribute("categories", ingCatService.getAllIngridients());
		return "recipes_add";
	}
	
	@RequestMapping(value="/addrecipe", method=RequestMethod.POST)
	public @ResponseBody String formAddRecipe(@RequestBody Recipe recipe, Principal principal) {
		RecipesValidation validation = new RecipesValidation();
		String whatToValidate = validation.validateRecipeForm(recipe);
		
		if ( whatToValidate.isEmpty() ) {
			try {
				FileUtils.moveFile(new File("tmp_images/" + recipe.getImage()), new File("images/" + recipe.getImage()));
				
				User user = userService.findByUsername(principal.getName());
				recipe.setUser(user);
				recipe.setDateField(new Date(System.currentTimeMillis()));
				recipe.setUsersLiked(new HashSet<Long>(Arrays.asList((long) 1)));
				recipesService.saveRecipe(recipe);

				return "";
			} catch(Exception ex) {
				System.out.println("Saving Recipe Error" + ex.getMessage());
				return "error";
			}
		}
		
		else {
			return whatToValidate;
		}	
	}
	
	@RequestMapping(value="/updaterecipe", method=RequestMethod.POST)
	public @ResponseBody String formUpdateRecipe(@RequestBody Recipe recipe, Principal principal) {
		
		RecipesValidation validation = new RecipesValidation();
		String whatToValidate = validation.validateRecipeForm(recipe);
		
		if (whatToValidate.isEmpty()) {
			try {
				Recipe original_recipe = recipesService.findRecipeById(recipe.getId());
				if (SecurityContextHolder.getContext().getAuthentication().getName().equals(original_recipe.getUser().getUsername())) {
					
					original_recipe.setTitle(recipe.getTitle());
					original_recipe.setFreetext(recipe.getFreetext());
					original_recipe.setTime(recipe.getTime());
					original_recipe.setAmount(recipe.getAmount());
					original_recipe.setProducts(recipe.getProducts());
					original_recipe.setPrices(recipe.getPrices());
					original_recipe.setPrice(recipe.getPrice());
					original_recipe.setVegetarian(recipe.isVegetarian());
					original_recipe.setVegan(recipe.isVegan());
					original_recipe.setKosher(recipe.isKosher());
					original_recipe.setSpicy(recipe.isSpicy());
					original_recipe.setGluten(recipe.isGluten());
					original_recipe.setRecipetime(recipe.getRecipetime());
					original_recipe.setRecipetype(recipe.getRecipetype());
					
					if ( !recipe.getImage().equals(original_recipe.getImage()) ) {
						File img = new File("images/" + original_recipe.getImage());
						FileUtils.deleteQuietly(img);
						original_recipe.setImage(recipe.getImage());
						FileUtils.moveFile(new File("tmp_images/" + recipe.getImage()), new File("images/" + recipe.getImage()));
					}
					
					recipesService.saveRecipe(original_recipe);
					return "";
				}
				return "error";
			} catch ( Exception ex) {
				return "error";
			}
		}
		else {
			return whatToValidate;
		}
	}
	
	@RequestMapping(value="/user/{username}/recipes", method=RequestMethod.GET)
	public String userRecipes(@PathVariable String username, Principal principal, Model model) {
		
		try {
			if (username.equals(principal.getName())) {
				User user = userService.findByUsername(username);
				if ( user != null) {
					model.addAttribute("user_recipes", recipesService.getAllUserRecipes(user.getId()));
					return "recipes_user";
				} 
				else {
					return "redirect:///";
					// return "No such user"
				}
			} 
			else {
				return "redirect:///";
				// return "User doesn't allowed 
			}
		} 
		catch (Exception ex) {
			return "redirect:///";
			// Error
		}	
	}
	
	@RequestMapping(value="/user/{username}/liked/recipes", method=RequestMethod.GET)
	public String userRecipesLiked(@PathVariable String username, Principal principal, Model model) {
		try {
			if (username.equals(principal.getName())) {
				User user = userService.findByUsername(username);
				if ( user != null ) {
					model.addAttribute("recipes", recipesService.getUserRecipesLiked((long) user.getId()));
					return "recipes_liked";
				}
				else {
					return "redirect:sorry";
				}
			}
			else {
				return "redirect:sorry";
			}
		}
		catch (Exception ex) {
			return "redirect:sorry";
		}
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String searchRecipe(@ModelAttribute("search") Search search, RedirectAttributes ra) {
		 ra.addFlashAttribute("search_results", search);
		 return "redirect:/search_results";
		
	}
	
	@RequestMapping(value="/search_results", method=RequestMethod.GET)
	public String searchResults(@ModelAttribute("search_results") Search search, Model model, Principal principal) {	
		try { 
			long id = userService.findByUsername(principal.getName()).getId();
			model.addAttribute("user_id", id);
			
		}catch (Exception ex) {	}
		 
		 if (search.getPrice() == 0) {
			 search.setPrice(999);
		 }
		 if (search.getTime() == 0) {
			 search.setTime(999);
		 }
		
		 Iterable<Recipe> recipes = recipesService.getSearchRecipes(search.getPrice(), search.getRecipetime(),
				 search.getRecipetype(), search.getFreetext().trim(), search.getTime(), search.isVegetarian(),
				 search.isVegan(), search.isKosher(), search.isSpicy(), search.isGluten());
		
		 model.addAttribute("recipes", recipes);
		return "search_results";
	}
	
	private void addPhotosModel(Model model) {
		
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
	}
}
