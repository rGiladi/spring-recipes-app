package com.roy.controllers;

import java.io.File;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.roy.models.Menu;
import com.roy.models.Recipe;
import com.roy.models.Search;
import com.roy.models.User;
import com.roy.repositories.MenusRepository;
import com.roy.security.Services.UserService;
import com.roy.services.IngridientsCategoryService;
import com.roy.services.MenusService;
import com.roy.validation.MenusValidator;

@Controller
public class MenusController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MenusService menusService;
	
	@Autowired
	private MenusRepository menusRepo;
	
	@Autowired
	private MenusValidator menuValidator;
	
	@Autowired
	private IngridientsCategoryService ingCatService;
	
	@RequestMapping(value="/menus", method=RequestMethod.GET)
	public String menus(Principal principal, Model model) {
		try { 
			long id = userService.findByUsername(principal.getName()).getId();
			model.addAttribute("user_id", id);
		}catch (Exception ex) {	}
		
		if (!menusRepo.AllMenusPaging(21).isEmpty()) { // If there are more pages to show
			model.addAttribute("more_menus", true);
		}
		
		model.addAttribute("menus", menusRepo.AllMenusPaging(0));
		addPhotosModel(model);
		return "menus";

	}
	
	@RequestMapping(value="/menus/page/{n}", method=RequestMethod.GET)
	public String menusPagination(@PathVariable int n, Principal principal, Model model) {
		if ( n > 1) {
			try { 
				long id = userService.findByUsername(principal.getName()).getId();
				model.addAttribute("user_id", id);
			}catch (Exception ex) {	}
			
			List<Menu> menus = menusRepo.AllMenusPaging(n * 20 - 19);
			
			System.out.println(n * 20 - 19);
			
			if (menus.isEmpty()) {
				addPhotosModel(model);
				return "redirect:/no-more-recipes";
			}
			
			if (!menusRepo.AllMenusPaging(n * 20 + 1).isEmpty()) { // If there are more pages to show
				model.addAttribute("more_page", n+1);
			}
			
			model.addAttribute("page", n);
			model.addAttribute("menus", menus);
			
			return "menus_paging";
		}
		
		addPhotosModel(model);
		return "redirect:/error";

	}
	
	@RequestMapping(value="/addmenu", method=RequestMethod.GET)
	public String addMenu(Menu menu, Model model) {
		Map<String, String> collectionFields = new HashMap<>();
		for (int i = 1; i < 9; i++) {
			collectionFields.put("productsMeal" + i, "productsMeal" + i);
			collectionFields.put("pricesMeal" + i, "pricesMeal" + i);
		}
		model.addAllAttributes(collectionFields);
		model.addAttribute("categories", ingCatService.getAllIngridients() );
		return "menus_add";
	}
	
	@RequestMapping(value="/addmenu", method=RequestMethod.POST)
	public @ResponseBody String formAddMenu(@RequestBody Menu menu, Principal principal) {
		
		String whatToValidate = menuValidator.validateMenuForm(menu);
		
		if ( whatToValidate.isEmpty() ) {
			User user = userService.findByUsername(principal.getName());
			
			menu.setUser(user);
			menu.setPublished(1);
			menu.setPub_date(new Date(System.currentTimeMillis()));
			
			menusService.saveMenu(menu);
			
			return "";
		} 
		
		else {
			
			return whatToValidate;
		
		}
	}
	
	@RequestMapping(value="/updatemenu", method=RequestMethod.POST)
	public @ResponseBody String formEditMenu(@RequestBody Menu menu, Principal principal) {
		
		String whatToValidate = menuValidator.validateMenuForm(menu);
		
		if ( whatToValidate.isEmpty() ) {
			try {
				Menu original_menu = menusService.findMenuById(menu.getId());
				if ( SecurityContextHolder.getContext().getAuthentication().getName().equals(original_menu.getUser().getUsername()) ) {
					original_menu.setTitle(menu.getTitle());
					original_menu.setFreeText(menu.getFreeText());
					original_menu.setPrice(menu.getPrice());
					original_menu.setProductsRecipe1(menu.getProductsRecipe1());
					original_menu.setProductsRecipe2(menu.getProductsRecipe2());
					original_menu.setProductsRecipe3(menu.getProductsRecipe3());
					original_menu.setProductsRecipe4(menu.getProductsRecipe4());
					original_menu.setProductsRecipe5(menu.getProductsRecipe5());
					original_menu.setProductsRecipe6(menu.getProductsRecipe6());
					original_menu.setProductsRecipe7(menu.getProductsRecipe7());
					original_menu.setProductsRecipe8(menu.getProductsRecipe8());
					original_menu.setPricesRecipe1(menu.getPricesRecipe1());
					original_menu.setPricesRecipe2(menu.getPricesRecipe2());
					original_menu.setPricesRecipe3(menu.getPricesRecipe3());
					original_menu.setPricesRecipe4(menu.getPricesRecipe4());
					original_menu.setPricesRecipe5(menu.getPricesRecipe5());
					original_menu.setPricesRecipe6(menu.getPricesRecipe6());
					original_menu.setPricesRecipe7(menu.getPricesRecipe7());
					original_menu.setPricesRecipe8(menu.getPricesRecipe8());
					
					menusService.saveMenu(original_menu);
					
					return "";
				}
				
				else {
					return "err_edit_user";
				}
				
			} catch ( Exception ex) {
				System.out.println("Exception editing menu -> " + ex.getMessage());
				return "err_edit";
			}
		} 
		
		else {
			
			return whatToValidate;
		
		}
	}
	
	@RequestMapping(value="/user/{username}/menus", method=RequestMethod.GET)
	public String userMenus(@PathVariable String username, Principal principal, Model model) {
		try { 
			if (username.equals(principal.getName())) {
				User user = userService.findByUsername(username);
				if ( user != null ) {
					model.addAttribute("user_menus", menusService.getUserMenus(user.getId()));
					return "menus_user";
				}
				else {
					// return no such user
					return "redirect://";
				}
			}
			else {
				// return user doesn't allowed
				return "redirect://";
			}
		}
		catch (Exception ex) {
			return "redirect://";
		}
	}
	
	@RequestMapping(value="/user/{username}/liked/menus", method=RequestMethod.GET)
	public String userMenusLiked(@PathVariable String username, Principal principal, Model model) {
		try {
			if (username.equals(principal.getName())) {
				User user = userService.findByUsername(username);
				if ( user != null ) {
					model.addAttribute("menus", menusService.getUserMenusLiked((long) user.getId()));
					return "menus_liked";
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
