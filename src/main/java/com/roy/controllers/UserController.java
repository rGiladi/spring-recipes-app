package com.roy.controllers;

import java.io.File;
import java.security.Principal;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.roy.models.Recipe;
import com.roy.models.Menu;
import com.roy.models.User;
import com.roy.repositories.RoleRepository;
import com.roy.security.Services.UserService;
import com.roy.services.RecipeService;
import com.roy.services.IngridientsCategoryService;
import com.roy.services.MenusService;



@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecipeService recipesService;
	
	@Autowired
	private MenusService menusService;
	
	@Autowired 
	private IngridientsCategoryService ingCatService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	// Recipes
	
	@RequestMapping(value="/user/{username}/edit_recipe/{recipe_id}", method=RequestMethod.GET)
	public String editRecipe(@PathVariable String username, @PathVariable long recipe_id, Principal principal,
			Model model) {
		try {
			if (SecurityContextHolder.getContext().getAuthentication().getName().equals(username)) {
				Recipe recipe = recipesService.findRecipeById(recipe_id);
				if (recipe.getUser().getUsername().equals(username)) {
					model.addAttribute("e_recipe", recipe);
					model.addAttribute("recipe", new Recipe());
					model.addAttribute("categories", ingCatService.getAllIngridients());
					return "recipes_edit";
				}
			}
		} 
		
		// Not Allowed
		catch(Exception ex) {
		}
		
		return "redirect:/sorry";
	}
	
	@RequestMapping(value="/user/deleteRecipe", method=RequestMethod.POST) 
	public @ResponseBody String deleteRecipe(@RequestParam(value="recipe", required=true) long recipe_id, Principal principal) {
		try {
			Recipe req_recipe = recipesService.findRecipeById(recipe_id);
			User user = userService.findByUsername(principal.getName());
	
			if ( req_recipe.getUser().getId() == user.getId() || user.getRoles().contains(roleRepository.findOne((long) 2))) {
				File img = new File("images/" + req_recipe.getImage());
				FileUtils.deleteQuietly(img);
				recipesService.deleteRecipe(recipe_id);
				return "1";
			} else {
				// Someone is trying to alter the user's recipe
				return "permission";
			}
		} catch (Exception ex) {
			System.out.println(ex);
			return "error";
		}
	}
	
	@RequestMapping(value="/user/likeRecipe", method=RequestMethod.POST)
	public @ResponseBody String likeRecipe(@RequestParam(value="recipe", required=true) long recipe_id, Principal principal) {
		try {
			if ( principal != null ) {
				User user = userService.findByUsername(principal.getName());
				
				Recipe req_recipe = recipesService.findRecipeById(recipe_id);
				Set<Long> usersLiked = req_recipe.getUsersLiked();
				
				if ( !usersLiked.contains(user.getId()) ) {
					usersLiked.add(user.getId());
					req_recipe.setUsersLiked(usersLiked);
					recipesService.saveRecipe(req_recipe);
					
					return "1";
				}
				
				else {
					for ( Iterator<Long> iter = usersLiked.iterator(); iter.hasNext(); ) {
						Long id = iter.next();
						
						if (id.equals(user.getId())) {
							iter.remove();
						}
					}
					
					req_recipe.setUsersLiked(usersLiked);
					recipesService.saveRecipe(req_recipe);
					
					return "0";
				}
			} else {
				return "user";
			}
			
		} catch (Exception ex) {
			return "error";
		}
	}
	
	// Menus
	
	@RequestMapping(value="/user/{username}/edit_menu/{menu_id}", method=RequestMethod.GET)
	public String editMenu(@PathVariable String username, @PathVariable long menu_id, Principal principal,
			Model model) {
		try {
			if (SecurityContextHolder.getContext().getAuthentication().getName().equals(username)) {
				Menu menu = menusService.findMenuById(menu_id);
				if (menu.getUser().getUsername().equals(username)) {
					model.addAttribute("e_menu", menu);
					model.addAttribute("menu", new Menu());
					model.addAttribute("categories", ingCatService.getAllIngridients());
					return "menus_edit";
				}
			}
		} 
		
		// Not Allowed
		catch(Exception ex) {
			System.out.println("Exception" + ex.getMessage());
		}
		
		return "redirect:/sorry";
	}
	
	@RequestMapping(value="/user/updatePublishedMenu", method=RequestMethod.POST)
	public @ResponseBody String updatePublishedMenu(@RequestParam(value="menu", required=true) long menu_id, Principal principal) {
		try {
			Menu req_menu = menusService.findMenuById(menu_id);
			User user = userService.findByUsername(principal.getName());
			
			if ( req_menu.getUser().getId() == user.getId() ) {
				
				if (req_menu.getPublished() == 0) {
					req_menu.setPublished(1);
					menusService.saveMenu(req_menu);
					return "1";
				}
				else {
					req_menu.setPublished(0);
					menusService.saveMenu(req_menu);
					return "0";
				}
			}
			else {
				// Someone is trying to alter the user's menu
				return "permission";
			}
			
		}catch(Exception ex) {
			return "error";
		}
	}
	
	@RequestMapping(value="/user/deleteMenu", method=RequestMethod.POST) 
	public @ResponseBody String deleteMenu(@RequestParam(value="menu", required=true) long menu_id, Principal principal) {
		try {
			Menu req_menu = menusService.findMenuById(menu_id);
			User user = userService.findByUsername(principal.getName());
			
			if ( req_menu.getUser().getId() == user.getId() || user.getRoles().contains(roleRepository.findOne((long) 2)) ) {
				menusService.deleteMenu(menu_id);
				return "1";
			} else {
				// Someone is trying to alter the user's menu
				return "permission";
			}
		} catch (Exception ex) {
			System.out.println(ex);
			return "error";
		}
	}
	
	@RequestMapping(value="/user/likeMenu", method=RequestMethod.POST)
	public @ResponseBody String likeMenu(@RequestParam(value="menu", required=true) long menu_id, Principal principal) {
		try {
			if ( principal != null ) {
				User user = userService.findByUsername(principal.getName());
				
				Menu req_recipe = menusService.findMenuById(menu_id);
				Set<Long> usersLiked = req_recipe.getUsersLiked();
				
				if ( !usersLiked.contains(user.getId()) ) {
					usersLiked.add(user.getId());
					req_recipe.setUsersLiked(usersLiked);
					menusService.saveMenu(req_recipe);
					
					return "1";
				}
				
				else {
					for ( Iterator<Long> iter = usersLiked.iterator(); iter.hasNext(); ) {
						Long id = iter.next();
						if ( id.equals(user.getId()) ) {
							iter.remove();
						}
					}
					
					req_recipe.setUsersLiked(usersLiked);
					menusService.saveMenu(req_recipe);
					
					return "0";
				}
			} 
			else {
				return "user";
			}
		} catch (Exception ex) {
			return "error";
		}
	}
	
	// Both
	
	@RequestMapping(value="/user/report", method=RequestMethod.POST)
	public @ResponseBody String reportRecipeOrMenu(@RequestParam(name="m") int m, @RequestParam(name="id") long id
			, Principal principal) {
		/*
		 * int m 
		 * m = 1 -> Menu
		 * m = 2 -> Recipe
		 * 
		 * return
		 * 1 = report
		 * 2 = unreport
		 */
		
		if (principal == null ) {
			return "user";
		}
		else {
			User user = userService.findByUsername(principal.getName());
			long user_id = user.getId();
			
			if ( m == 1 ) {
				Menu menu = menusService.findMenuById(id);
				Set<Long> usersReported = menu.getUsersReported();
				
				if ( usersReported.contains(user_id) ) {
					usersReported.remove(user_id);
					menu.setUsersReported(usersReported);
					menu.setReported(false);
					menusService.saveMenu(menu);
					return "2";
				} 
				else {
					usersReported.add(user_id);
					menu.setUsersReported(usersReported);
					menu.setReported(true);
					menusService.saveMenu(menu);
					return "1";
				}
			}
			
			
			else {
				Recipe recipe = recipesService.findRecipeById(id);
				Set<Long> usersReported = recipe.getUsersReported();
				
				if ( usersReported.contains(user_id) ) {
					usersReported.remove(user_id);
					recipe.setUsersReported(usersReported);
					recipe.setReported(false);
					recipesService.saveRecipe(recipe);
					return "2";
				} 
				else {
					usersReported.add(user_id);
					recipe.setUsersReported(usersReported);
					recipe.setReported(true);
					recipesService.saveRecipe(recipe);
					return "1";
				}
			}
		}
		
	}
}
